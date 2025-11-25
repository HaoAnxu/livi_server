package com.anxu.smarthomeunity.service.impl;

import com.anxu.smarthomeunity.mapper.CommunityInfoMapper;
import com.anxu.smarthomeunity.mapper.WeCommunityMapper;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoRelaEntity;
import com.anxu.smarthomeunity.service.WeCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * WeCommunity相关服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:04
 */
@Service
public class WeCommunityServiceImpl implements WeCommunityService {

    @Autowired
    private CommunityInfoMapper communityInfoMapper;
    @Autowired
    private WeCommunityMapper weCommunityMapper;

    /**
     * 保存消息到数据库-返回消息ID
     * @param communityInfoEntity 消息实体类Entity
     * @return msg_id
     */
    @Override
    public Long saveGroupMessage(CommunityInfoEntity communityInfoEntity) {
        // 保存消息到数据库
        boolean result = communityInfoMapper.insert(communityInfoEntity) > 0;
        if(result){
            Long msgId = communityInfoEntity.getMsgId();
            return msgId;
        }
        return null;
    }

    /**
     * 获取圈子所有成员ID
     * @param circleId 圈子ID
     * @return 成员ID列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<Integer> getCircleAllMembers(Integer circleId) {
        return weCommunityMapper.selectUserIdsByCircleId(circleId);
    }

    /**
     * 保存用户-消息关联信息到数据库
     * @param communityInfoRelaEntity 关联实体类Entity
     */
    @Override
    public void saveUserMessageConnect(CommunityInfoRelaEntity communityInfoRelaEntity) {
        weCommunityMapper.insert(communityInfoRelaEntity);
    }

    /**
     * 更新用户-消息关联信息-已读状态
     * @param msgId 消息ID
     * @param userId 用户ID
     */
    @Override
    public void updateReadStatus(Long msgId, Integer userId) {
        weCommunityMapper.updateReadStatus(msgId, userId);
    }

    /**
     * 查询用户-消息关联信息-未读消息
     * @param circleId 圈子ID
     * @param userId 用户ID
     * @return 未读消息列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<CommunityInfoEntity> getOfflineMessages(Integer circleId, Integer userId) {
        return weCommunityMapper.queryNoReadInfo(circleId, userId);
    }

}
