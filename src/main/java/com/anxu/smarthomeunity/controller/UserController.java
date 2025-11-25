package com.anxu.smarthomeunity.controller;

import com.alibaba.fastjson2.JSONObject;
import com.anxu.smarthomeunity.model.Result.Result;
import com.anxu.smarthomeunity.model.dto.user.UserInfoDto;
import com.anxu.smarthomeunity.model.entity.user.UserInfoEntity;
import com.anxu.smarthomeunity.service.UserService;
import com.anxu.smarthomeunity.util.CodeUtils;
import com.anxu.smarthomeunity.util.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

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
        log.info("发送验证码请求：{}", email);
        String code = CodeUtils.generateVerifyCode();
        log.info("生成的验证码：{}", code);
        userService.sendVerifyCode(email, code);
        // 存储验证码到Redis（有效期5分钟，用于后续校验）
        redisTemplate.opsForValue().set("verify_code:" + email, code, 5, TimeUnit.MINUTES);
        return Result.success();
    }

    //校验验证码
    @PostMapping("/user/verifyCode")
    public Result verifyCode(@RequestParam String email, @RequestParam String code) {
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

    //用户中心_基础信息查询
    @GetMapping("/permission/user/userCenter/basicInfo")
    public Result userCenterBasicInfo(@RequestParam Integer userId){
        log.info("用户中心_基础信息查询请求：{}", userId);
        UserInfoDto userInfoDto = userService.getUserInfo(userId);
        if (userInfoDto == null) {
            return Result.error("用户不存在");
        }
        return Result.success(userInfoDto);
    }
}
