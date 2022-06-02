package com.gmy.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gmy.blog.dto.user.UserBackDTO;
import com.gmy.blog.entity.UserAuthEntity;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.PageUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户账号
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Repository
public interface UserAuthDao extends BaseMapper<UserAuthEntity> {

    /**
     * 获取 置顶条件下 用户的数量
     * @param condition 条件
     * @return 用户数量
     */
    Integer countUser(@Param("condition")ConditionVO condition);

    /**
     * 查询后台用户列表
     *
     * @param limitCurrent   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List<UserBackDTO>} 用户列表
     */
    List<UserBackDTO> listUsers(@Param("current") Long limitCurrent,
                                @Param("size") Long size,
                                @Param("condition") ConditionVO condition);
}
