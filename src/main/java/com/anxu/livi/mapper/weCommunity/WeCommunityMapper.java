package com.anxu.livi.mapper.weCommunity;

import com.anxu.livi.model.dto.weCommunity.ChatInfoDetailDTO;
import com.anxu.livi.model.entity.weCommunity.ChatInfoRelaEntity;
import com.anxu.livi.model.entity.weCommunity.CommunityInfoEntity;
import com.anxu.livi.model.entity.weCommunity.CommunityUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * WeCommunity-Mapper接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 15:21
 */
@Mapper
public interface WeCommunityMapper{

    //根据社区ID查询所有成员ID
    List<Integer> selectUserIdsByCommunityId(Integer communityId);

    //插入消息-用户关联信息
    void insert(ChatInfoRelaEntity chatInfoRelaEntity);

    //更新阅读状态
    void updateReadStatus(Integer msgId, Integer userId);

    //查询用户-消息关联信息-未读消息
    List<ChatInfoDetailDTO> queryNoReadInfo(Integer communityId, Integer userId);

    //查询社区信息列表
    List<CommunityInfoEntity> getAllCommunityList();

    //用户加入社区
    void joinCommunity(CommunityUserEntity communityUserEntity);

    //用户退出社区
    void deleteByCommunityIdAndUserId(Integer communityId, Integer userId);

    //更新社区用户数
    void updateCommunityUserCount(Integer communityId);

    //根据社区ID和用户ID查询用户是否加入了该社区
    CommunityUserEntity selectByCommunityIdAndUserId(Integer communityId, Integer userId);

    //根据社区ID查询社区详情
    CommunityInfoEntity queryCommunityDetail(Integer communityId);

    //查询聊天记录-分页
    List<ChatInfoDetailDTO> selectChatHistoryByPage(Integer communityId, Integer lastMsgId, Integer pageSize);
}
