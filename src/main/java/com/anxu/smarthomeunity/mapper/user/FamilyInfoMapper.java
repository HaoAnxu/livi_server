package com.anxu.smarthomeunity.mapper.user;

import com.anxu.smarthomeunity.model.entity.user.userFamilyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户家庭信息相关MP接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/11 14:08
 */
@Mapper
public interface FamilyInfoMapper extends BaseMapper<userFamilyEntity> {
}
