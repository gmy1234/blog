package com.gmy.blog.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.constant.RedisPrefixConst;
import com.gmy.blog.dao.UserAuthDao;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dao.UserRoleDao;
import com.gmy.blog.dto.EmailDTO;
import com.gmy.blog.dto.user.UserAreaDTO;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.dto.user.UserInfoDTO;
import com.gmy.blog.dto.user.UserRoleDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.entity.UserRoleEntity;
import com.gmy.blog.enums.LoginTypeEnum;
import com.gmy.blog.enums.RoleEnum;
import com.gmy.blog.enums.UserAreaTypeEnum;
import com.gmy.blog.exception.BizException;
import com.gmy.blog.service.BlogInfoService;
import com.gmy.blog.service.RedisService;
import com.gmy.blog.service.UserAuthService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.util.CommonUtils;
import com.gmy.blog.util.IpUtils;
import com.gmy.blog.util.UserUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.PageUtils;
import com.gmy.blog.vo.Result;
import com.gmy.blog.vo.user.UserDetailDTO;
import com.gmy.blog.vo.user.UserLoginVo;
import com.gmy.blog.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;

import org.apache.catalina.Manager;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.gmy.blog.constant.CommonConst.*;
import static com.gmy.blog.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.gmy.blog.constant.MQPrefixConst.EMAIL_ROUTING_KEY;
import static com.gmy.blog.constant.RedisPrefixConst.*;

/**
 * ??????????????????
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

    @Autowired
    private BlogInfoService blogInfoService;

    @Override
    public PageResult<UserBackDTO> getAllUsers(ConditionVO condition) {
        // ????????????????????????
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
        // ???????????? ?????????
        if (!CommonUtils.checkEmail(email)) {
            throw new BizException("?????????????????????????????????????????????");
        }
        // ???????????????
        String randomCode = CommonUtils.getRandomCode();
        // ???????????????
        EmailDTO emailDTO = EmailDTO.builder()
                .email(email)
                .subject("?????????")
                .content("????????????????????? " + randomCode + ", ????????????15?????????????????????????????????")
                .build();

        // ????????????????????????????????????????????????????????????
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, EMAIL_ROUTING_KEY, emailDTO);
        // ?????? Redis???????????????????????????????????? 15??????
        redisService.set(REGISTER_VERIFICATION_KEY + email, randomCode, CODE_EXPIRE_TIME);

    }

    /**
     * ?????????????????????????????????
     *
     * @param user ??????
     * @return true
     */
    private Boolean checkUser(UserVO user) {

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            throw new BizException("???????????????????????????");
        }

        // ????????????????????????
        Object code = redisService.get(REGISTER_VERIFICATION_KEY + user.getUsername());
        if (code != null && !code.equals(user.getCode())) {
            throw new BizException("???????????????");
        }

        // ??????????????????????????????
        UserAuthEntity userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuthEntity>()
                .select(UserAuthEntity::getUsername)
                .eq(UserAuthEntity::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }

    @Override
    public void register(UserVO userVo) {
        // ???????????????
        if (this.checkUser(userVo)) {
            throw new BizException("??????????????????");
        }
        // ?????????????????????
        UserInfoEntity userInfo = UserInfoEntity.builder()
                .email(userVo.getUsername())
                .nickname(DEFAULT_NICKNAME + IdWorker.getId())
                // ??????????????????
                .avatar(blogInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoDao.insert(userInfo);

        // ?????????????????????
        UserRoleEntity userRole = UserRoleEntity.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleDao.insert(userRole);

        // ????????????
        UserAuthEntity userAuth = UserAuthEntity.builder()
                .userInfoId(userInfo.getId())
                .username(userVo.getUsername())
                .password(BCrypt.hashpw(userVo.getPassword())) // ??????????????????
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthDao.insert(userAuth);
    }


    @Override
    public UserInfoDTO login(UserLoginVo userVo) {
        log.info("????????? login ?????????");
        // AuthenticationManager ??????????????????
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // ?????????????????????????????????
        if (Objects.isNull(authenticate)) {
            throw new BizException("????????????");
        }
        // ???????????????
        // ???????????????????????????????????????????????????UserDetailServiceImpl??????????????? loadUserByUsername???????????????
        // userInfo ?????????????????????????????????
        UserDetailDTO userInfo = (UserDetailDTO) authenticate.getPrincipal();
        // ??????userId
        Integer userInfoId = userInfo.getUserInfoId();
        // ?????????????????????
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userInfoId.toString());
        // ?????? JWT ?????????????????????UserID ?????? Token???
        String token = JWTUtil.createToken(payload, "token".getBytes(StandardCharsets.UTF_8));
        // ????????????????????? Redis??? userid ??? key??????????????????????????? 15??????
        redisService.set("login:" + userInfoId, userInfo, 24 * 60 * 60 );

        // ????????????????????????
        UserInfoDTO userLoginInfo = BeanCopyUtils.copyObject(userInfo, UserInfoDTO.class);
        userLoginInfo.setToken(token);
        // ????????????IP???????????????????????????
        this.updateUserInfo(userLoginInfo);
        return userLoginInfo;
    }

    /**
     * ??????????????????
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
        // ?????? SecurityContextHolder ????????????ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailDTO loginUser = (UserDetailDTO) authentication.getPrincipal();

        // ?????? redis ??????????????????
        Integer userInfoId = loginUser.getUserInfoId();
        redisService.del("login:" + userInfoId);

        return "????????????";
    }

    @Override
    public List<UserAreaDTO> listUserAreas(ConditionVO conditionVO) {
        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();
        switch (Objects.requireNonNull(UserAreaTypeEnum.getUserAreaType(conditionVO.getType()))) {
            case USER:
                Object userArea = redisService.get(USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOList = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOList;
            // ??????
            case VISITOR:
                // ????????????????????????
                Map<String, Object> visitorArea = redisService.hGetAll(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOList = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOList;
            default:
                break;
        }
        return userAreaDTOList;
    }

    @Override
    public void resetPassword(UserVO userVO) {
        // ??????????????????
        if (!checkUser(userVO)) {
            throw new BizException("?????????????????????");
        }

        userAuthDao.update(new UserAuthEntity(), new LambdaUpdateWrapper<UserAuthEntity>()
                .set(UserAuthEntity::getPassword, BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()))
                .eq(UserAuthEntity::getUsername, userVO.getUsername()));
    }


}
