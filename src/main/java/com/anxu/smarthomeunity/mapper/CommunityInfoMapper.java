package com.anxu.smarthomeunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * WeCommunity相关Mapper接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
@Mapper
public interface CommunityInfoMapper extends BaseMapper<CommunityInfo> {
}
