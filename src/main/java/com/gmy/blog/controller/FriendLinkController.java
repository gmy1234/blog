package com.gmy.blog.controller;


import com.gmy.blog.annotation.OptLog;
import com.gmy.blog.dto.FriendLinkBackDTO;
import com.gmy.blog.dto.FriendLinkDTO;
import com.gmy.blog.service.FriendLinkService;
import com.gmy.blog.vo.ConditionVO;
import com.gmy.blog.vo.FriendLinkVO;
import com.gmy.blog.vo.PageResult;
import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * 友链控制器
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
@Api(tags = "友链模块")
@RestController
public class FriendLinkController {
    @Autowired
    private FriendLinkService friendLinkService;

    /**
     * 查看友链列表
     *
     * @return {@link Result<FriendLinkDTO>} 友链列表
     */
    @ApiOperation(value = "查看友链列表")
    @GetMapping("/links")
    public Result<List<FriendLinkDTO>> listFriendLinks() {
        List<FriendLinkDTO> res = friendLinkService.listFriendLinks();
        return Result.ok(res);
    }

    /**
     * 查看后台友链列表
     *
     * @param condition 条件
     * @return {@link Result<FriendLinkBackDTO>} 后台友链列表
     */
    @ApiOperation(value = "查看后台友链列表")
    @GetMapping("/admin/links")
    public Result<PageResult<FriendLinkBackDTO>> listFriendLinkDTO(ConditionVO condition) {
        PageResult<FriendLinkBackDTO> res = friendLinkService.listFriendLinkDTO(condition);
        return Result.ok(res);
    }

    /**
     * 保存或修改友链
     *
     * @param friendLinkVO 友链信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "保存或修改友链")
    @PostMapping("/admin/links/saveOrUpdate")
    public Result<?> saveOrUpdateFriendLink(@Valid @RequestBody FriendLinkVO friendLinkVO) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVO);
        return Result.ok();
    }

    /**
     * 删除友链
     *
     * @param info 友链id列表  key: data, value IdList
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除友链")
    @DeleteMapping("/admin/links/delete")
    public Result<?> deleteFriendLink(@RequestBody Map<String ,List<Integer>> info) {
        friendLinkService.removeByIds(info.get("data"));
        return Result.ok();
    }

}

