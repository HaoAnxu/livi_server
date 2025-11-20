package com.anxu.smarthomeunity.service.impl;

import com.anxu.smarthomeunity.mapper.UserMapper;
import com.anxu.smarthomeunity.pojo.user.UserInfo;
import com.anxu.smarthomeunity.service.UserService;
import com.anxu.smarthomeunity.util.JwtStaticProxy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    //    密码加密器
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailSender mailSender;

    //    用户登录
    @Override
    public String login(UserInfo userInfo) {
        //后端也应有校验
        Assert.isTrue(StringUtils.hasText(userInfo.getUsername()), "用户名不能为空");
        Assert.isTrue(StringUtils.hasText(userInfo.getPassword()), "密码不能为空");

        //根据用户名查询用户
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userInfo.getUsername());
        UserInfo user = userMapper.selectOne(queryWrapper);
        Assert.notNull(user, "用户不存在");
        //校验密码
        boolean matches = passwordEncoder.matches(userInfo.getPassword(), user.getPassword());
        Assert.isTrue(matches, "密码错误");

        //登录成功，生成JWT令牌然后返回
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",user.getId());
        dataMap.put("username",user.getUsername());
        return JwtStaticProxy.generateJwt(dataMap);
    }
    //    用户注册
    @Override
    public Integer register(UserInfo userInfo) {
        //后端也应有校验
        Assert.isTrue(StringUtils.hasText(userInfo.getUsername()), "用户名不能为空");
        Assert.isTrue(StringUtils.hasText(userInfo.getPassword()), "密码不能为空");
        Assert.isTrue(StringUtils.hasText(userInfo.getEmail()), "邮箱不能为空");
        Assert.isTrue(userInfo.getEmail().matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$"), "邮箱格式错误");
        Assert.isTrue(userInfo.getPassword().length() >= 6, "密码长度不能小于6位");
        //设置时间
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userInfo.getUsername());
        UserInfo user = userMapper.selectOne(queryWrapper);
        Assert.isTrue(user == null, "用户名已存在");

        queryWrapper.eq("email",userInfo.getEmail());
        user = userMapper.selectOne(queryWrapper);
        Assert.isTrue(user == null, "邮箱已存在");

        //加密
        String encrypt = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encrypt);

        //插入数据库
        return userMapper.insert(userInfo);
    }

    //    发送邮箱验证码
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendVerifyCode(String email, String code) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("haomarch7@qq.com");
            message.setTo(email);
            message.setSubject("验证码");
            message.setText("您的验证码为：" + code);
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
