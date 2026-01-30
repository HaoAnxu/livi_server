package com.anxu.livi_module_user.service;

import com.anxu.livi_model.model.vo.user.UserInfoVO;
import com.anxu.livi_model.model.entity.user.UserInfoEntity;

/**
 * 用户相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
public interface UserService {
    //    登录
    String login(UserInfoEntity userInfoEntity);
    //    注册
    Integer register(UserInfoEntity userInfoEntity);
    //    发送验证码
    void sendVerifyCode(String email, String code);
    //    用户中心_基础信息查询
    UserInfoVO getUserInfo(Integer userId);
    //根据用户名查询用户id
    Integer getUserId(String username);
    //根据邮箱查询邮箱是否存在
    boolean isEmailExist(String email);
    //判断用户名是否已经存在
    boolean isUsernameExist(String username);
    //    用户中心，修改用户非敏感信息
    Integer updateUserInfo(UserInfoEntity userInfoEntity);
    //    用户中心，修改用户密码
    Integer updateUserPassword(UserInfoEntity userInfoEntity);
}
