package com.gmy.blog.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.constant.RedisPrefixConst;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dao.UserRoleDao;
import com.gmy.blog.dto.EmailDTO;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.dto.user.UserInfoDTO;
import com.gmy.blog.dto.user.UserRoleDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.entity.UserRoleEntity;
import com.gmy.blog.enums.LoginTypeEnum;
import com.gmy.blog.enums.RoleEnum;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.util.CommonUtils;
import com.gmy.blog.util.UserUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import com.gmy.blog.vo.user.UserDetailDTO;
import com.gmy.blog.vo.user.UserLoginVo;
import com.gmy.blog.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.Manager;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gmy.blog.constant.CommonConst.DEFAULT_NICKNAME;
import static com.gmy.blog.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.gmy.blog.constant.MQPrefixConst.EMAIL_ROUTING_KEY;
import static com.gmy.blog.constant.RedisPrefixConst.CODE_EXPIRE_TIME;
import static com.gmy.blog.constant.RedisPrefixConst.REGISTER_VERIFICATION_KEY;

/**
 * 用户账号服务
 *
 * @author gmy
 * @date 2022/06/01
 */
@Slf4j
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuthEntity> implements UserAuthService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public PageResult<UserBackDTO> getAllUsers(ConditionVO condition) {
        // 获取后台用户数量
        // condition.keywords != null
        // condition.loginType != null
        Integer count = userAuthDao.countUser(condition);
        if (count == 0) {
            return new PageResult<>();
        }

        List<UserBackDTO> allUser = userAuthDao.listUsers(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);

        return new PageResult<>(allUser, count);
    }

    @Override
    public void sendCode(String email) {
        // 检验是否 为邮箱
        if (!CommonUtils.checkEmail(email)) {
            throw new BizException("邮箱格式错误，请输入正确的邮箱");
        }
        // 生产验证码
        String randomCode = CommonUtils.getRandomCode();
        // 封装实体类
        EmailDTO emailDTO = EmailDTO.builder()
                .email(email)
                .subject("验证码")
                .content("你的验证码为： " + randomCode + ", 有效期为15分钟，请不要透露给别人")
                .build();

        // 使用消息队列，将发送验证码任务放到队列中
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_ROUTING_KEY, emailDTO);
        // 使用 Redis，存放验证码，过期时间为 15分钟
        redisService.set(REGISTER_VERIFICATION_KEY + email, randomCode, CODE_EXPIRE_TIME);

    }

    /**
     * 检查用户的数据是否正确
     *
     * @param user 用户
     * @return true
     */
    private Boolean checkUser(UserVO user) {
        // 校验用户的验证码
        Object code = redisService.get(REGISTER_VERIFICATION_KEY + user.getUsername());
        if (code != null && !code.equals(user.getCode())) {
            throw new BizException("验证码错误");
        }

//        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
//            throw new BizException("验证码错误！");
//        }
        // 校验邮箱是否已经注册
        UserAuthEntity userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuthEntity>()
                .select(UserAuthEntity::getUsername)
                .eq(UserAuthEntity::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }

    @Override
    public void register(UserVO userVo) {
        // 校验有错误
        if (this.checkUser(userVo)) {
            throw new BizException("用户已被注册");
        }
        // 新增用户的信息
        UserInfoEntity userInfo = UserInfoEntity.builder()
                .email(userVo.getUsername())
                .nickname(DEFAULT_NICKNAME + IdWorker.getId())
                // TODO：等网站配置，然后设置默认头像
                .avatar(null)
                .build();
        userInfoDao.insert(userInfo);

        // 绑定用户的角色
        UserRoleEntity userRole = UserRoleEntity.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleDao.insert(userRole);

        // 新增账号
        UserAuthEntity userAuth = UserAuthEntity.builder()
                .userInfoId(userInfo.getId())
                .username(userVo.getUsername())
                .password(BCrypt.hashpw(userVo.getPassword())) // 使用盐值加密
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthDao.insert(userAuth);
    }


    @Override
    public UserInfoDTO login(UserLoginVo userVo) {
        log.info("执行了 login 方法：");
        // AuthenticationManager 进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 认证没有通过，抛出异常
        if (Objects.isNull(authenticate)){
            throw new BizException("认证失败");
        }
        // 认证通过。
        // 获取认证后返回的数据（我们自定义的UserDetailServiceImpl实现类中的 loadUserByUsername（）方法）
        // userInfo 就是这个方法的返回类。
        UserDetailDTO userInfo = (UserDetailDTO) authenticate.getPrincipal();
        // 获取userId
        Integer userInfoId = userInfo.getUserInfoId();
        // 放到信息载荷中
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userInfoId.toString());
        // 使用 JWT 的工具类，通过UserID 生成 Token，
        String token = JWTUtil.createToken(payload, "token".getBytes(StandardCharsets.UTF_8));

        // 把用户信息存入 Redis， userid 为 key
        redisService.set("login:" + userInfoId, userInfo);

        // 把用户的信息返回
        UserInfoDTO userLoginInfo = BeanCopyUtils.copyObject(userInfo, UserInfoDTO.class);
        userLoginInfo.setToken(token);
        // 更新用户IP地址，最近登陆时间
        this.updateUserInfo(userLoginInfo);
        return userLoginInfo;
    }

    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo(UserInfoDTO userLoginInfo) {
        UserAuthEntity userAuth = UserAuthEntity.builder()
                .id(userLoginInfo.getId())
                .ipAddress(userLoginInfo.getIpAddress())
                .ipSource(userLoginInfo.getIpSource())
                .lastLoginTime(userLoginInfo.getLastLoginTime())
                .build();
        userAuthDao.updateById(userAuth);
    }

    @Override
    public String logout() {
        // 获取 SecurityContextHolder 中用户的ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDTO loginUser = (UserDetailDTO) authentication.getPrincipal();

        // 删除 redis 里的用户数据
        Integer userInfoId = loginUser.getUserInfoId();
        redisService.del("login:" + userInfoId);

        return "注销成功";
    }


}
