package com.anxu.smarthomeunity.controller;

import com.alibaba.fastjson2.JSON;
import com.anxu.smarthomeunity.model.Result.WebSocketResult;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoEntity;
import com.anxu.smarthomeunity.model.entity.wecommunity.CommunityInfoRelaEntity;
import com.anxu.smarthomeunity.service.WeCommunityService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WeCommunityController
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
@Component
@Slf4j
@ServerEndpoint("/ws/community/{circleId}/{userId}")
public class WeCommunityController {

    //全局静态唯一Map：存储所有连接实例（key: circleId:userId）
    private static final ConcurrentHashMap<String, WeCommunityController> ONLINE_USER_MAP = new ConcurrentHashMap<>();
    //全局静态唯一Map：存储每个圈子的在线用户ID集合（key: circleId）
    private static final ConcurrentHashMap<Integer, Set<Integer>> CIRCLE_ONLINE_USER_IDS_MAP = new ConcurrentHashMap<>();

    //每个实例独有的属性
    private Session session;
    private Integer circleId;  // 圈子ID（从路径参数获取）
    private Integer userId;    // 用户ID（从路径参数获取）
    private String uniqueKey;  // 连接唯一标识：circleId:userId

    // 依赖注入（WebSocket多实例，需静态注入）
    private static WeCommunityService weCommunityService;

    @Autowired
    public void setWeCommunityService(WeCommunityService weCommunityService) {
        WeCommunityController.weCommunityService = weCommunityService;
    }

    /**
     * 连接建立成功时触发（关键：用@PathParam接收路径参数）
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("circleId") Integer circleId, @PathParam("userId") Integer userId) {
        this.session = session;
        this.circleId = circleId;
        this.userId = userId;
        this.uniqueKey = circleId + ":" + userId;

        log.info("用户[{}]尝试连接圈子[{}]，连接标识：{}", userId, circleId, uniqueKey);

        // 加入在线用户Map和圈子在线列表
        ONLINE_USER_MAP.put(uniqueKey, this);
        // 先根据圈子ID，查有没有对应的在线用户集合
        Set<Integer> onlineUserIds = CIRCLE_ONLINE_USER_IDS_MAP.get(circleId);
        //如果没有这个集合（说明这个圈子第一次有人上线）
        if (onlineUserIds == null) {
            onlineUserIds = ConcurrentHashMap.newKeySet();
            // 把“圈子ID-在线集合”存入 Map
            CIRCLE_ONLINE_USER_IDS_MAP.put(circleId, onlineUserIds);
        }
        //把当前用户ID加入在线集合
        onlineUserIds.add(userId);

        log.info("用户[{}]连接圈子[{}]成功！当前圈子在线人数：{}",
                userId, circleId, CIRCLE_ONLINE_USER_IDS_MAP.get(circleId).size());

        // 上线后补推离线消息
        pushOfflineMessages();
    }

    /**
     * 收到前端消息时触发
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            log.info("收到圈子[{}]用户[{}]的消息：{}", circleId, userId, message);

            //JSON转消息实体
            CommunityInfoEntity communityInfoEntity = JSON.parseObject(message, CommunityInfoEntity.class);
            //补全必要字段（从路径参数获取，前端不用传）
            communityInfoEntity.setCircleId(circleId);    // 圈子ID
            communityInfoEntity.setFromUserId(userId);    // 发送者ID

            //保存消息到数据库，获取自增msgId
            Long msgId = weCommunityService.saveGroupMessage(communityInfoEntity);
            if (msgId == null) {
                log.error("消息发送失败：数据库存储失败");
                WebSocketResult webSocketResult = new WebSocketResult("error", "消息发送失败：数据库存储失败");
                sendMessage(JSON.toJSONString(webSocketResult));
                return;
            }
            //把msgId回写到实体，供后面的方法用，无需定义外部变量
            communityInfoEntity.setMsgId(msgId);

            //广播消息给圈子所有成员（在线+离线）
            broadcastMessage(communityInfoEntity);

        } catch (Exception e) {
            log.error("处理消息失败", e);
            WebSocketResult webSocketResult = new WebSocketResult("error", "消息发送失败：" + e.getMessage());
            sendMessage(JSON.toJSONString(webSocketResult));
        }
    }

    /**
     * 连接关闭时触发
     */
    @OnClose
    public void onClose() {
        log.info("用户[{}]断开圈子[{}]连接", userId, circleId);

        // 1. 从在线用户Map移除
        ONLINE_USER_MAP.remove(uniqueKey);

        // 2. 从圈子在线列表移除
        Set<Integer> onlineUserIds = CIRCLE_ONLINE_USER_IDS_MAP.get(circleId);
        if (onlineUserIds != null) {
            onlineUserIds.remove(userId);
            // 如果圈子没人在线了，移除圈子缓存
            if (onlineUserIds.isEmpty()) {
                CIRCLE_ONLINE_USER_IDS_MAP.remove(circleId);
                log.info("圈子[{}]无在线用户，清除缓存", circleId);
            }
        }

        // 3. 释放session
        this.session = null;
    }

    /**
     * 连接出错时触发
     */
    @OnError
    public void onError(Throwable throwable) {
        log.error("用户[{}]圈子[{}]连接异常", userId, circleId, throwable);

        // 出错时主动关闭连接
        if (this.session != null && this.session.isOpen()) {
            try {
                this.session.close();
            } catch (IOException e) {
                log.error("关闭异常连接失败", e);
            }
        }

        // 清理在线状态
        ONLINE_USER_MAP.remove(uniqueKey);
        Set<Integer> onlineUserIds = CIRCLE_ONLINE_USER_IDS_MAP.get(circleId);
        if (onlineUserIds != null) {
            onlineUserIds.remove(userId);
        }
    }

    /**
     * 广播消息给圈子所有成员（在线+离线）
     */
    private void broadcastMessage(CommunityInfoEntity communityInfoEntity) {
        try {
            String messageJson = JSON.toJSONString(communityInfoEntity);
            WebSocketResult realtimeResult = new WebSocketResult("realtime", messageJson);
            String pushMessage = JSON.toJSONString(realtimeResult);

            // 1. 查圈子所有成员（从用户-圈子关联表 pub_community_and_user）
            List<Integer> allMemberIds = weCommunityService.getCircleAllMembers(circleId);
            if (allMemberIds == null || allMemberIds.isEmpty()) {
                log.info("圈子[{}]无成员，无需广播", circleId);
                return;
            }

            // 2. 获取当前圈子在线用户列表
            Set<Integer> onlineUserIds = CIRCLE_ONLINE_USER_IDS_MAP.getOrDefault(circleId, ConcurrentHashMap.newKeySet());

            // 3. 遍历所有成员，区分在线/离线处理
            for (Integer memberId : allMemberIds) {
                if (onlineUserIds.contains(memberId)) {
                    // 在线成员：推送消息 + 创建已读关联记录
                    String onlineUniqueKey = circleId + ":" + memberId;
                    WeCommunityController onlineController = ONLINE_USER_MAP.get(onlineUniqueKey);

                    if (onlineController != null) {
                        //发送带有type的json
                        onlineController.sendMessage(pushMessage);
                        //数据库插入已读关联记录
                        CommunityInfoRelaEntity connect = new CommunityInfoRelaEntity();
                        connect.setUserId(memberId);
                        connect.setMsgId(communityInfoEntity.getMsgId());
                        connect.setCircleId(circleId);
                        connect.setReadStatus(1); // 已读
                        weCommunityService.saveUserMessageConnect(connect);
                        log.info("已给在线成员[{}]推送消息并创建已读记录", memberId);
                    } else {
                        // 异常：在线用户无连接，按离线处理
                        handleOfflineMember(communityInfoEntity, memberId);
                    }
                } else {
                    // 离线成员：创建未读关联记录
                    handleOfflineMember(communityInfoEntity, memberId);
                }
            }

        } catch (Exception e) {
            log.error("广播消息失败", e);
        }
    }

    /**
     * 处理离线成员：创建未读关联记录
     */
    private void handleOfflineMember(CommunityInfoEntity communityInfoEntity, Integer memberId) {
        try {
            CommunityInfoRelaEntity connect = new CommunityInfoRelaEntity();
            connect.setUserId(memberId);
            connect.setMsgId(communityInfoEntity.getMsgId());
            connect.setCircleId(circleId);
            connect.setReadStatus(0); // 未读
            weCommunityService.saveUserMessageConnect(connect);
            log.info("已给离线成员[{}]创建未读关联记录", memberId);
        } catch (Exception e) {
            log.error("处理离线成员[{}]消息失败", memberId, e);
        }
    }

    /**
     * 补推离线消息（用户上线后）
     */
    private void pushOfflineMessages() {
        try {
            // 1. 查数据库：用户在该圈子的未读消息
            List<CommunityInfoEntity> offlineMessages = weCommunityService.getOfflineMessages(circleId, userId);
            if (offlineMessages == null || offlineMessages.isEmpty()) {
                log.info("用户[{}]圈子[{}]无离线消息", userId, circleId);
                return;
            }

            // 2. 遍历补推
            log.info("给用户[{}]补推离线消息{}条", userId, offlineMessages.size());
            for (CommunityInfoEntity offlineMsg : offlineMessages) {
                String messageJson = JSON.toJSONString(offlineMsg);
                WebSocketResult realtimeResult = new WebSocketResult("offline", messageJson);
                String pushMessage = JSON.toJSONString(realtimeResult);
                //发送带有type的json
                sendMessage(pushMessage);
                // 3. 补推成功，更新为已读
                weCommunityService.updateReadStatus(offlineMsg.getMsgId(), userId);
                log.info("已补推消息[{}]给用户[{}]并更新为已读", offlineMsg.getMsgId(), userId);
            }

        } catch (Exception e) {
            log.error("补推离线消息失败", e);
        }
    }

    /**
     * 给当前连接的用户发消息
     */
    private void sendMessage(String message) {
        try {
            if (this.session != null && this.session.isOpen()) {
                this.session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            log.error("给用户[{}]发消息失败：{}", userId, message, e);
        }
    }
}