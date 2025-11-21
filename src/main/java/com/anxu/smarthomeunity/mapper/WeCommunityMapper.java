package com.anxu.smarthomeunity.mapper;

import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityAndUser;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfo;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoConnect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * WeCommunity相关数据库操作Mapper
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 15:21
 */
@Mapper
public interface WeCommunityMapper{

    //根据圈子ID查询所有成员ID
    List<Integer> selectUserIdsByCircleId(Integer circleId);

    //插入消息-用户关联信息
    void insert(CommunityInfoConnect communityInfoConnect);

    //更新阅读状态
    void updateReadStatus(Long msgId, Integer userId);

    //查询用户-消息关联信息-未读消息
    List<CommunityInfo> queryNoReadInfo(Integer circleId, Integer userId);
}
