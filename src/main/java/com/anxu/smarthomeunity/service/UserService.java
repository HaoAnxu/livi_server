package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.dto.user.UserInfoDto;
import com.anxu.smarthomeunity.model.entity.user.UserInfoEntity;

public interface UserService {
    //    登录
    String login(UserInfoEntity userInfoEntity);
    //    注册
    Integer register(UserInfoEntity userInfoEntity);
    //    发送验证码
    void sendVerifyCode(String email, String code);
    //    用户中心_基础信息查询
    UserInfoDto getUserInfo(Integer userId);
    //根据用户名查询用户id
    Integer getUserId(String username);
}
