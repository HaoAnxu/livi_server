package com.anxu.livi_module_user.service.impl;

import com.anxu.livi_module_user.mapper.user.UserMapper;
import com.anxu.livi_model.model.entity.user.UserInfoEntity;
import com.anxu.livi_module_user.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 【管理员服务实现类】
 *
 * @Author: haoanxu
 * @Date: 2025/12/23
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    //    密码加密器
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserMapper userMapper;

    //    管理员注册
    @Override
    public Integer register(UserInfoEntity userInfoEntity) {
        //后端也应有校验
        Assert.isTrue(StringUtils.hasText(userInfoEntity.getUsername()), "用户名不能为空");
        Assert.isTrue(StringUtils.hasText(userInfoEntity.getPassword()), "密码不能为空");
        Assert.isTrue(userInfoEntity.getPassword().length() >= 6, "密码长度不能小于6位");
        //设置时间
        userInfoEntity.setCreateTime(LocalDateTime.now());
        userInfoEntity.setUpdateTime(LocalDateTime.now());

        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userInfoEntity.getUsername());
        UserInfoEntity user = userMapper.selectOne(queryWrapper);
        Assert.isTrue(user == null, "用户名已存在");

        //加密
        String encrypt = passwordEncoder.encode(userInfoEntity.getPassword());
        userInfoEntity.setPassword(encrypt);

        //插入数据库
        return userMapper.insert(userInfoEntity);
    }
}
