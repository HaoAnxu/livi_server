package com.anxu.livi_module_user.service;

import com.anxu.livi_model.model.entity.user.UserInfoEntity;

/**
 * 管理员服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/23 16:47
 */
public interface AdminService {
    Integer register(UserInfoEntity userInfoEntity);
}
