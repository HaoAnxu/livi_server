package com.anxu.smarthomeunity.mapper;

import com.anxu.smarthomeunity.model.entity.user.UserInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 用户相关Mapper接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfoEntity> {
}
