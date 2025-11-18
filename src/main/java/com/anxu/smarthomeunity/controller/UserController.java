package com.anxu.smarthomeunity.controller;

import com.anxu.smarthomeunity.pojo.Result.Result;
import com.anxu.smarthomeunity.pojo.user.UserInfo;
import com.anxu.smarthomeunity.service.UserService;
import com.anxu.smarthomeunity.util.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody UserInfo userInfo) {
        log.info("登录请求：{}", userInfo);
        String token = userService.login(userInfo);
        if (token == null) {
            return Result.error("登录失败,Token为空");
        }
        return Result.success(token);
    }
    //注册
    @PostMapping("/register")
    public Result register(@RequestBody UserInfo userInfo) {
        log.info("注册请求：{}", userInfo);
        Integer rows = userService.register(userInfo);
        if (rows == 0) {
            return Result.error("注册失败");
        }
        return Result.success("注册成功");
    }

//    //发送验证码
//    @PostMapping("/sendCode")
//    public Result sendCode(@RequestBody UserInfo userInfo) {
//        log.info("发送验证码请求：{}", userInfo);
//        String code = CodeUtils.generateVerifyCode();
//        log.info("生成的验证码：{}", code);
//        userService.sendVerifyCode(userInfo.getEmail(), code);
//        // 存储验证码到Redis（有效期5分钟，用于后续校验）
//        redisTemplate.opsForValue().set("verify_code:" + userInfo.getEmail(), code, 5, TimeUnit.MINUTES);
//        return Result.success();
//    }
//
//    //校验验证码
//    @GetMapping("/verifyCode")
//    public Result verifyCode(@RequestBody UserInfo userInfo) {
//        String storedCode = redisTemplate.opsForValue().get("verify_code:" + userInfo.getEmail()).toString();
//        if (storedCode == null) {
//            return Result.error("验证码不存在");
//        }
//        if (!storedCode.equals(userInfo.getVerifyCode())) {
//            return Result.error("验证码错误");
//        }
//        // 验证码校验成功后，删除Redis中的验证码
//        redisTemplate.delete("verify_code:" + userInfo.getEmail());
//        return Result.success("验证码校验成功");
//    }
}
