package com.anxu.livi_module_user.mapper.user;

import com.anxu.livi_model.model.entity.user.UserInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户相关MP接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfoEntity> {
    //根据社区id查询所有成员
    List<UserInfoEntity> selectMemberByCommunityId(Integer communityId);
}
