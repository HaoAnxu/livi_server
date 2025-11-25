package com.anxu.smarthomeunity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.smarthomeunity.mapper.UserMapper;
import com.anxu.smarthomeunity.model.dto.user.UserInfoDto;
import com.anxu.smarthomeunity.model.entity.user.UserInfoEntity;
import com.anxu.smarthomeunity.service.UserService;
import com.anxu.smarthomeunity.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
/**
 * 用户服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/11/25 8:54
 */
@Service
public class UserServiceImpl implements UserService {

    //    密码加密器
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private JwtUtils jwtUtils;

    //    用户登录
    @Override
    public String login(UserInfoEntity userInfoEntity) {
        //后端也应有校验
        Assert.isTrue(StringUtils.hasText(userInfoEntity.getUsername()), "用户名不能为空");
        Assert.isTrue(StringUtils.hasText(userInfoEntity.getPassword()), "密码不能为空");

        //根据用户名查询用户
        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userInfoEntity.getUsername());
        UserInfoEntity user = userMapper.selectOne(queryWrapper);
        Assert.notNull(user, "用户不存在");
        //校验密码
        boolean matches = passwordEncoder.matches(userInfoEntity.getPassword(), user.getPassword());
        Assert.isTrue(matches, "密码错误");

        //登录成功，生成JWT令牌然后返回
        return jwtUtils.generateJwt(user.getId(),user.getUsername());
    }

    //    用户注册
    @Override
    public Integer register(UserInfoEntity userInfoEntity) {
        //后端也应有校验
        Assert.isTrue(StringUtils.hasText(userInfoEntity.getUsername()), "用户名不能为空");
        Assert.isTrue(StringUtils.hasText(userInfoEntity.getPassword()), "密码不能为空");
        Assert.isTrue(StringUtils.hasText(userInfoEntity.getEmail()), "邮箱不能为空");
        Assert.isTrue(userInfoEntity.getEmail().matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$"), "邮箱格式错误");
        Assert.isTrue(userInfoEntity.getPassword().length() >= 6, "密码长度不能小于6位");
        //设置时间
        userInfoEntity.setCreateTime(LocalDateTime.now());
        userInfoEntity.setUpdateTime(LocalDateTime.now());

        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userInfoEntity.getUsername());
        UserInfoEntity user = userMapper.selectOne(queryWrapper);
        Assert.isTrue(user == null, "用户名已存在");

        queryWrapper.eq("email", userInfoEntity.getEmail());
        user = userMapper.selectOne(queryWrapper);
        Assert.isTrue(user == null, "邮箱已存在");

        //加密
        String encrypt = passwordEncoder.encode(userInfoEntity.getPassword());
        userInfoEntity.setPassword(encrypt);

        //插入数据库
        return userMapper.insert(userInfoEntity);
    }

    //    发送邮箱验证码
    @Async("taskExecutor")
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
    //    用户中心_基础信息查询
    @Override
    public UserInfoDto getUserInfo(Integer userId) {
        UserInfoEntity userInfoEntity = userMapper.selectById(userId);
        //校验用户是否存在
        Assert.isTrue(userInfoEntity != null, "用户不存在");
        //将实体转换为DTO
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtil.copyProperties(userInfoEntity, userInfoDto);
        return userInfoDto;
    }

    @Override
    public Integer getUserId(String username) {
        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserInfoEntity user = userMapper.selectOne(queryWrapper);
        Assert.isTrue(user != null, "用户不存在");
        return user.getId();
    }
}
