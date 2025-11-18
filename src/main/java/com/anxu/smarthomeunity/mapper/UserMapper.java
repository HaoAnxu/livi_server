package com.anxu.smarthomeunity.mapper;

import com.anxu.smarthomeunity.pojo.user.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {
}
