package com.anxu.livi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.livi.mapper.weCommunity.ChatInfoMapper;
import com.anxu.livi.mapper.user.UserMapper;
import com.anxu.livi.mapper.weCommunity.WeCommunityMapper;
import com.anxu.livi.model.dto.weCommunity.ChatHistoryQueryDTO;
import com.anxu.livi.model.dto.weCommunity.ChatInfoDetailDTO;
import com.anxu.livi.model.entity.user.UserInfoEntity;
import com.anxu.livi.model.vo.user.UserInfoVO;
import com.anxu.livi.model.vo.weCommunity.ChatHistoryVO;
import com.anxu.livi.model.vo.weCommunity.CommunityInfoVO;
import com.anxu.livi.model.entity.weCommunity.ChatInfoEntity;
import com.anxu.livi.model.entity.weCommunity.ChatInfoRelaEntity;
import com.anxu.livi.model.entity.weCommunity.CommunityInfoEntity;
import com.anxu.livi.model.entity.weCommunity.CommunityUserEntity;
import com.anxu.livi.service.WeCommunityService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * WeCommunity相关服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:04
 */
@Slf4j
@Service
public class WeCommunityServiceImpl implements WeCommunityService {

    @Autowired
    private ChatInfoMapper chatInfoMapper;
    @Autowired
    private WeCommunityMapper weCommunityMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 保存消息到数据库-返回消息ID
     *
     * @param chatInfoEntity 消息实体类Entity
     * @return msg_id
     */
    @Override
    public Integer saveGroupMessage(ChatInfoEntity chatInfoEntity) {
        // 保存消息到数据库
        boolean result = chatInfoMapper.insert(chatInfoEntity) > 0;
        if (result) {
            Integer msgId = chatInfoEntity.getMsgId();
            return msgId;
        }
        return null;
    }

    /**
     * 获取圈子所有成员ID
     *
     * @param communityId 圈子ID
     * @return 成员ID列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<Integer> getCommunityAllMembers(Integer communityId) {
        return weCommunityMapper.selectUserIdsByCommunityId(communityId);
    }

    /**
     * 保存用户-消息关联信息到数据库
     *
     * @param chatInfoRelaEntity 关联实体类Entity
     */
    @Override
    public void saveUserMessageConnect(ChatInfoRelaEntity chatInfoRelaEntity) {
        weCommunityMapper.insert(chatInfoRelaEntity);
    }

    /**
     * 更新用户-消息关联信息-已读状态
     *
     * @param msgId  消息ID
     * @param userId 用户ID
     */
    @Override
    public void updateReadStatus(Integer msgId, Integer userId) {
        weCommunityMapper.updateReadStatus(msgId, userId);
    }

    /**
     * 查询用户-消息关联信息-未读消息
     *
     * @param communityId 圈子ID
     * @param userId      用户ID
     * @return 未读消息列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<ChatInfoDetailDTO> getOfflineMessages(Integer communityId, Integer userId) {
        return weCommunityMapper.queryNoReadInfo(communityId, userId);
    }

    /**
     * 获取所有社区信息列表并且更新用户数
     *
     * @return 社区信息列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<CommunityInfoVO> getAllCommunityList() {
        //更新所有圈子用户数
        weCommunityMapper.getAllCommunityList().forEach(
                communityInfoEntity -> {
                    weCommunityMapper.updateCommunityUserCount(communityInfoEntity.getCommunityId());
                }
        );
        //再查一遍
        List<CommunityInfoEntity> communityList = weCommunityMapper.getAllCommunityList();
        if (communityList == null) {
            return List.of();
        } else {
            return BeanUtil.copyToList(communityList, CommunityInfoVO.class);
        }
    }

    /**
     * 用户加入社区
     *
     * @param communityId 社区ID
     * @param userId      用户ID
     */
    @Override
    public void joinCommunity(Integer communityId, Integer userId) {
        // 保存用户-社区关联信息到数据库
        CommunityUserEntity communityUserEntity = new CommunityUserEntity();
        communityUserEntity.setCommunityId(communityId);
        communityUserEntity.setUserId(userId);
        communityUserEntity.setCreateTime(LocalDateTime.now());
        communityUserEntity.setUpdateTime(LocalDateTime.now());
        weCommunityMapper.joinCommunity(communityUserEntity);
        //更新社区用户数量
        weCommunityMapper.updateCommunityUserCount(communityId);
    }

    /**
     * 用户退出社区
     *
     * @param communityId 社区ID
     * @param userId      用户ID
     */
    @Override
    public void exitCommunity(Integer communityId, Integer userId) {
        // 删除用户-社区关联信息
        weCommunityMapper.deleteByCommunityIdAndUserId(communityId, userId);
    }

    /**
     * 查询用户是否加入了该社区
     *
     * @param communityId 社区ID
     * @param userId      用户ID
     * @return true-加入了该社区，false-未加入该社区
     */
    @Override
    public boolean isJoinCommunity(Integer communityId, Integer userId) {
        // 查询用户-社区关联信息是否存在
        CommunityUserEntity communityUserEntity = weCommunityMapper.selectByCommunityIdAndUserId(communityId, userId);
        return communityUserEntity != null;
    }

     /**
     * 查询单个社区详情
     *
     * @param communityId 社区ID
     * @return 社区详情（无数据时返回null）
     */
    @Override
    public CommunityInfoVO getCommunityDetail(Integer communityId) {
        // 查询社区详情
        CommunityInfoEntity communityInfoEntity = weCommunityMapper.queryCommunityDetail(communityId);
        if (communityInfoEntity == null) {
            return null;
        }
        return BeanUtil.copyProperties(communityInfoEntity, CommunityInfoVO.class);
    }

    /**
     * 查询聊天记录
     *
     * @param queryDTO 查询参数
     * @return 聊天记录VO（无数据时返回null）
     */
    @Override
    public ChatHistoryVO getChatHistory(ChatHistoryQueryDTO queryDTO) {
        ChatHistoryVO result = new ChatHistoryVO();

        // 1. 分页查询：msgId < lastMsgId 的前pageSize条，按msgId降序
        List<ChatInfoDetailDTO> historyList = weCommunityMapper.selectChatHistoryByPage(
                queryDTO.getCommunityId(),
                queryDTO.getLastMsgId(),
                queryDTO.getPageSize()
        );

        // 2. 封装返回结果
        result.setHistoryList(historyList);

        // 3. 判断是否还有更多数据：如果查到的条数等于pageSize，说明可能还有；否则没有
        boolean hasMore = !CollectionUtils.isEmpty(historyList) && historyList.size() == queryDTO.getPageSize();
        result.setHasMore(hasMore);

        // 4. 设置当前最老的msgId（供下一次查询用）
        if (!CollectionUtils.isEmpty(historyList)) {
            // 因为按msgId降序，最后一条是最老的
            result.setCurrentLastMsgId(historyList.get(historyList.size() - 1).getMsgId());
        } else {
            result.setCurrentLastMsgId(queryDTO.getLastMsgId()); // 无数据时设为查询的lastMsgId
        }

        //给列表每个元素添加用户名和头像
        historyList.forEach(
                chatInfoDetailDTO -> {
                    chatInfoDetailDTO.setName(userMapper.selectById(chatInfoDetailDTO.getFromUserId()).getUsername());
                    chatInfoDetailDTO.setAvatar(userMapper.selectById(chatInfoDetailDTO.getFromUserId()).getAvatar());
                }
        );
        return result;
    }

    /**
     * 查询社区所有成员信息
     *
     * @param communityId 社区ID
     * @return 成员信息列表（无数据时返回空列表，避免null）
     */
    @Override
    public List<UserInfoVO> getCommunityAllMembersInfo(Integer communityId) {
        List<UserInfoEntity> userInfoEntityList = userMapper.selectMemberByCommunityId(communityId);
        if (userInfoEntityList == null) {
            return List.of();
        } else {
            return BeanUtil.copyToList(userInfoEntityList, UserInfoVO.class);
        }
    }
}
