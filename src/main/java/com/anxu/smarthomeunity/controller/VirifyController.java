package com.anxu.smarthomeunity.controller;

import com.anxu.smarthomeunity.model.Result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 简单验证登录状态
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 16:50
 */
@RestController
@Slf4j
public class VirifyController {
    @GetMapping("/permission/isLogin")
    public Result isLogin(){
        log.info("验证登录状态-已经登陆-拦截验证通过");
        return Result.success();
    }
}
