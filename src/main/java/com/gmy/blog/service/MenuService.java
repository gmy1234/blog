package com.gmy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gmy.blog.dto.authority.MenuDTO;
import com.gmy.blog.entity.MenuEntity;
import com.gmy.blog.vo.ConditionVO;

import java.util.List;

/**
 * 菜单服务
 *
 * @author gmy
 * @date 2022/05/29
 */
public interface MenuService extends IService<MenuEntity> {


    /**
     * 查询菜单
     * @param conditionVO 条件
     * @return 菜单集
     */
    List<MenuDTO> listMenus(ConditionVO conditionVO);
}
