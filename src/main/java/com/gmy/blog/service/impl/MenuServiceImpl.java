package com.gmy.blog.service.impl;

import cn.hutool.core.stream.CollectorUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmy.blog.dao.MenuDao;
import com.gmy.blog.dto.authority.LabelOptionDTO;
import com.gmy.blog.dto.authority.MenuDTO;
import com.gmy.blog.entity.MenuEntity;
import com.gmy.blog.service.MenuService;
import com.gmy.blog.util.BeanCopyUtils;
import com.gmy.blog.vo.ConditionVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gmydl
 * @title: MenuService
 * @projectName blog-api
 * @description: 菜单服务
 * @date 2022/6/5 22:54
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuDTO> listMenus(ConditionVO conditionVO) {
        // 查询菜单
        List<MenuEntity> menuList = menuDao.selectList(new LambdaQueryWrapper<MenuEntity>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), MenuEntity::getName, conditionVO.getKeywords()));
        // 获取目录列表(父级菜单)
        List<MenuEntity> categoryLogList = this.listCategorylog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<MenuEntity>> childrenCategoryMap = this.getMenuMap(menuList);

        // 组装目录菜单数据
        List<MenuDTO> menuDTOList = categoryLogList.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtils.copyObject(item, MenuDTO.class);
            // 获取目录下的菜单排序
            List<MenuDTO> list = BeanCopyUtils.copyList(childrenCategoryMap.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            // 设置子菜单
            menuDTO.setChildren(list);
            childrenCategoryMap.remove(item.getId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum)).collect(Collectors.toList());

        // 诺还有菜单没有取出，则拼接
        if (CollectionUtils.isNotEmpty(childrenCategoryMap)){
            List<MenuEntity> childrenList = new ArrayList<>();

            // TODO:
            childrenCategoryMap.values().forEach(childrenList::addAll);

            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOList.addAll(childrenDTOList);
        }

        return menuDTOList;
    }

    /**
     * 获取父级菜单下的子菜单
     *
     * @param menuList 所以菜单
     * @return 子菜单
     */
    private Map<Integer, List<MenuEntity>> getMenuMap(List<MenuEntity> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                // Collectors.groupingBy(p1) :通过p1参数来进行分组，化成 map 集合
                .collect(Collectors.groupingBy(MenuEntity::getParentId));
    }

    /**
     * 获取 父级菜单
     *
     * @param menuList 菜单集合
     * @return 父级菜单
     */
    private List<MenuEntity> listCategorylog(List<MenuEntity> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .sorted(Comparator.comparing(MenuEntity::getOrderNum))
                .collect(Collectors.toList());

    }

}
