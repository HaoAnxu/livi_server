package com.anxu.smarthomeunity.server;

import com.alibaba.fastjson2.JSON;
import com.anxu.smarthomeunity.pojo.websocket.ChatMessage;
import com.anxu.smarthomeunity.pojo.websocket.ResultMsg;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WS_Chat服务类
 *
 * @Author: haoanxu
 * @Date: 2025/11/17 14:39
 */
// 注解说明：@ServerEndpoint 定义WebSocket端点地址（前端后续要连这个地址）
// {userId} 是路径参数，用于区分不同用户（比如前端传 userId=1001，就会被捕获）
@Component
@Slf4j
@ServerEndpoint("/ws/chat/{userId}")
public class ChatServer {
    // 存储所有在线的WebSocket连接：key=userId，value=当前连接对象
    // 存储在线用户：key=userId，value=当前连接对象（线程安全）
    private static ConcurrentHashMap<String, ChatServer> onlineUsers = new ConcurrentHashMap<>();

    //当前用户的WebSocket会话
    private Session session;

    //当前连接的用户ID
    private String userId;

    /**
     * 连接建立成功时出发（前端发起连接后，这里会触发）
     */
    @OnOpen
    //userId 路径参数，用于获取前端传递的用户ID
    public void onOpen(Session session, @PathParam("userId") String userId){
        this.session = session;
        this.userId = userId;
        //把当前连接存入map（用户上线）
        onlineUsers.put(userId,this);
        log.info("用户[{}]连接成功，当前在线人数：{}", userId, onlineUsers.size());
        ResultMsg successMsg = new ResultMsg("success", "✅ 连接成功！你是用户[" + userId + "]");
        sendMessage(JSON.toJSONString(successMsg));
    }
    /**
     * 收到前端消息时触发（前端发消息，后端这里接收）
     * @param message 前端发送的消息
     */
    @OnMessage
    public void onMessage(String message){
        log.info("用户[{}]发送消息：{}", userId, message);
        try {
            if("ping".equals(message)){
                sendMessage("pong");
                return;
            }
            ChatMessage chatMessage = JSON.parseObject(message,ChatMessage.class);
            String fromUserId = chatMessage.getFromUserId();
            String toUserId = chatMessage.getToUserId();
            String content = chatMessage.getContent();

            if(!userId.equals(fromUserId)){
                ResultMsg errorMsg = new ResultMsg("error", "你不是发送者[" + fromUserId + "]，不能发送消息！");
                sendMessage(JSON.toJSONString(errorMsg));
                return;
            }
            //给目标用户发消息
            ChatServer targetUser = onlineUsers.get(toUserId);
            if (targetUser != null) {
                // 目标用户在线，组装消息对象并发送JSON
                ChatMessage CM = new ChatMessage();
                CM.setType("chat_msg");
                CM.setFromUserId(fromUserId);
                CM.setToUserId(toUserId);
                CM.setContent(content);
                targetUser.sendMessage(JSON.toJSONString(CM));
                // 给发送者回“发送成功”（组装JSON）
                ResultMsg successMsg = new ResultMsg("success", "消息已发送给用户[" + toUserId + "]");
                sendMessage(JSON.toJSONString(successMsg));
            } else {
                ResultMsg errorMsg = new ResultMsg("error", "用户[" + toUserId + "]不在线");
                sendMessage(JSON.toJSONString(errorMsg));
            }
        } catch (Exception e) {
            ResultMsg errorMsg = new ResultMsg("error", "消息格式错误：" + e.getMessage());
            sendMessage(JSON.toJSONString(errorMsg));
        }
    }
    /**
     * 连接关闭时触发（前端断开连接、刷新页面、关闭浏览器时执行）
     */
    @OnClose
    public void onClose() {
        // 把当前连接从map中移除（用户下线）
        onlineUsers.remove(userId);
        log.info("用户[{}]断开连接，当前在线人数：{}", userId, onlineUsers.size());
        ResultMsg successMsg = new ResultMsg("success", "✅ 连接已断开！你是用户[" + userId + "]");
        sendMessage(JSON.toJSONString(successMsg));
    }
    /**
     * 连接出错时触发（比如网络异常）
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户[{}]连接出错：{}", userId, error.getMessage());
        ResultMsg errorMsg = new ResultMsg("error", "连接出错：" + error.getMessage());
        sendMessage(JSON.toJSONString(errorMsg));
    }
    /**
     * 给当前用户发消息（内部工具方法）
     */
    private void sendMessage(String message) {
        try {
            //getBasicRemote() 是「同步发送」,sendText(...)：是发送 “文本消息” 的方法
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            ResultMsg errorMsg = new ResultMsg("error", "给用户[" + userId + "]发消息失败：" + e.getMessage());
            sendMessage(JSON.toJSONString(errorMsg));
        }
    }
    
}
