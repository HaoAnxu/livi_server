package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.entity.user.UserInfo;

public interface UserService {
    //    登录
    String login(UserInfo userInfo);
    //    注册
    Integer register(UserInfo userInfo);
    //    发送验证码
    void sendVerifyCode(String email, String code);
}
