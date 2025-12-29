package com.anxu.livi.controller;

import com.alibaba.fastjson2.JSONObject;
import com.anxu.livi.model.result.Result;
import com.anxu.livi.model.vo.user.UserInfoVO;
import com.anxu.livi.model.entity.user.UserInfoEntity;
import com.anxu.livi.service.UserService;
import com.anxu.livi.util.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;
/**
 * 【用户信息相关接口】
 *
 * @Author: haoanxu
 * @Date: 2025/11/10 16:48
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @GetMapping("/user")
    public Result getUserInfo() {
        return Result.success("yes");
    }
    //登录
    @PostMapping("/user/login")
    public Result login(@RequestBody UserInfoEntity userInfoEntity) {
        log.info("登录请求：{}", userInfoEntity);
        String token = userService.login(userInfoEntity);
        Integer userId = userService.getUserId(userInfoEntity.getUsername());
        if (token == null) {
            return Result.error("登录失败,Token为空");
        }
        JSONObject localstorage = new JSONObject();
        localstorage.put("token", token);
        localstorage.put("username", userInfoEntity.getUsername());
        localstorage.put("userId", userId);
        return Result.success(localstorage);
    }
    //注册
    @PostMapping("/user/register")
    public Result register(@RequestBody UserInfoEntity userInfoEntity) {
        log.info("注册请求：{}", userInfoEntity);
        Integer rows = userService.register(userInfoEntity);
        if (rows == 0) {
            return Result.error("注册失败");
        }
        return Result.success("注册成功");
    }

    //发送验证码
    @PostMapping("/user/sendCode")
    public Result sendCode(@RequestParam String email) {
        //先判断邮箱是否存在
        if (userService.isEmailExist(email)) {
            return Result.error("邮箱已经存在，不能重复注册");
        }
        String code = CodeUtils.generateVerifyCode();
        log.info("发送验证码请求：{}，生成的验证码：{}", email, code);
        userService.sendVerifyCode(email, code);
        // 存储验证码到Redis（有效期5分钟，用于后续校验）
        redisTemplate.opsForValue().set("verify_code:" + email, code, 5, TimeUnit.MINUTES);
        return Result.success();
    }

    //校验验证码
    @PostMapping("/user/verifyCode")
    public Result verifyCode(@RequestParam String email, @RequestParam String code) {
        log.info("校验验证码请求：{}", email);
        String storedCode = null;
        try {
            storedCode = redisTemplate.opsForValue().get("verify_code:" + email).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (storedCode == null) {
            return Result.error("验证码不存在");
        }
        if (!storedCode.equals(code)) {
            return Result.error("验证码错误");
        }
        // 验证码校验成功后，删除Redis中的验证码
        redisTemplate.delete("verify_code:" + email);
        return Result.success();
    }

    //判断用户名是否已经存在
    @PostMapping("/user/checkUsername")
    public Result checkUsername(@RequestParam String username) {
        log.info("判断用户名是否已经存在请求：{}", username);
        if (userService.isUsernameExist(username)) {
            return Result.error("用户名已经存在");
        }
        return Result.success();
    }

    //用户中心_基础信息查询
    @GetMapping("/permission/user/userCenter/basicInfo")
    public Result userCenterBasicInfo(@RequestParam Integer userId){
        log.info("用户中心_基础信息查询请求：{}", userId);
        UserInfoVO userInfoVO = userService.getUserInfo(userId);
        if (userInfoVO == null) {
            return Result.error("用户不存在");
        }
        return Result.success(userInfoVO);
    }

    //用户中心，修改用户非敏感信息
    @PostMapping("/permission/user/userCenter/updateInfo")
    public Result userCenterUpdateInfo(@RequestBody UserInfoEntity userInfoEntity){
        log.info("用户中心，修改用户非敏感信息请求：{}", userInfoEntity);
        Integer rows = userService.updateUserInfo(userInfoEntity);
        if (rows == 0) {
            return Result.error("修改用户信息失败");
        }
        return Result.success("修改用户信息成功");
    }

    //用户中心，修改用户密码
    @PostMapping("/permission/user/userCenter/updatePassword")
    public Result userCenterUpdatePassword(@RequestBody UserInfoEntity userInfoEntity){
        log.info("用户中心，修改用户密码请求：{}", userInfoEntity);
        Integer rows = userService.updateUserPassword(userInfoEntity);
        if (rows == 0) {
            return Result.error("修改用户密码失败");
        }
        return Result.success("修改用户密码成功");
    }
}
