package com.gmy.blog.service.impl;


import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.UserInfoDao;
import com.gmy.blog.dto.user.UserOnlineDTO;
import com.gmy.blog.entity.UserInfoEntity;
import com.gmy.blog.enums.FilePathEnum;
import com.gmy.blog.service.UserInfoService;
import com.gmy.blog.strategy.context.UploadStrategyContext;
import com.gmy.blog.util.UserUtils;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.user.UserInfoVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息服务
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    @Override
    public PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO) {
        // TODO:获取在线用户spring aq
        // 获取security在线session
        return new PageResult<>();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateUserAvatar(MultipartFile file) {
        // 头像上传
        String avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        Integer userInfoId = UserUtils.getLoginUser().getUserInfoId();
        // 更新用户信息
        UserInfoEntity userInfo = UserInfoEntity.builder()
                .id(userInfoId)
                .avatar(avatar)
                .build();
        userInfoDao.updateById(userInfo);
        return avatar;
    }

    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        // 封装用户信息
        UserInfoEntity userInfo = UserInfoEntity.builder()
                .id(UserUtils.getLoginUser().getUserInfoId())
                .nickname(userInfoVO.getNickname())
                .intro(userInfoVO.getIntro())
                .webSite(userInfoVO.getWebSite())
                .build();
        userInfoDao.updateById(userInfo);
    }
}
