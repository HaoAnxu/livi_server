package com.anxu.livi_module_user.controller;

import com.anxu.livi_model.model.entity.user.UserInfoEntity;
import com.anxu.livi_model.model.result.Result;
import com.anxu.livi_module_user.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 【管理员Controller】
 *
 * @Author: haoanxu
 * @Date: 2025/12/23
 */
@Slf4j
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    //注册官方用户
    @PostMapping("/admin/register")
    public Result register(@RequestBody UserInfoEntity userInfoEntity) {
        log.info("注册官方用户请求：{}", userInfoEntity);
        Integer rows = adminService.register(userInfoEntity);
        if (rows == 0) {
            return Result.error("注册官方用户失败");
        }
        return Result.success("注册官方用户成功");
    }
}
