package com.anxu.smarthomeunity.controller;

import com.anxu.smarthomeunity.model.dto.Result.Result;
import com.anxu.smarthomeunity.model.entity.user.UserInfo;
import com.anxu.smarthomeunity.service.UserService;
import com.anxu.smarthomeunity.util.CodeUtils;
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
    public Result login(@RequestBody UserInfo userInfo) {
        log.info("登录请求：{}", userInfo);
        String token = userService.login(userInfo);
        if (token == null) {
            return Result.error("登录失败,Token为空");
        }
        Result result = new Result();
        result.setCode(1);
        result.setMsg(token);
        result.setData(userInfo.getUsername());
        return result;
    }
    //注册
    @PostMapping("/user/register")
    public Result register(@RequestBody UserInfo userInfo) {
        log.info("注册请求：{}", userInfo);
        Integer rows = userService.register(userInfo);
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

    //用户中心
    @GetMapping("/permission/user/userCenter")
    public Result userCenter(@RequestParam Integer userId){
        return Result.success();
    }
}
