package com.gmy.blog.controller;

import com.gmy.blog.vo.Result;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gmydl
 * @title: LoginController
 * @projectName blog
 * @description: 登陆功能
 * @date 2022/5/26 15:54
 */
@Api("登陆管理")
@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public Result<HashMap<String,String>>login(){

        HashMap<String,String> data = new HashMap<>(3);
        data.put("name", "admin");
        data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80");

        return Result.ok(data);
    }

    @GetMapping("/info")
    public Result<String> info(){

        return Result.ok("admin", "token");
    }


}
