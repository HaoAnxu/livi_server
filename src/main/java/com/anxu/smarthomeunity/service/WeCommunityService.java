package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoRelaEntity;

import java.util.List;

/**
 * WeCommunity相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:03
 */
public interface WeCommunityService {
    //保存消息到数据库-返回msg_id
    Long saveGroupMessage(CommunityInfoEntity communityInfoEntity);

    //获取圈子所有成员ID
    List<Integer> getCircleAllMembers(Integer circleId);

    //插入消息-用户关联信息
    void saveUserMessageConnect(CommunityInfoRelaEntity communityInfoRelaEntity);

    //更新阅读状态
    void updateReadStatus(Long msgId, Integer userId);

    //查询用户在对应圈子里的未读消息
    List<CommunityInfoEntity> getOfflineMessages(Integer circleId, Integer userId);
}
