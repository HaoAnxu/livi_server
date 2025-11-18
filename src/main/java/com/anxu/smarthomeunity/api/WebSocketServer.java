package com.anxu.smarthomeunity.api;

import com.alibaba.fastjson2.JSON;
import com.anxu.smarthomeunity.pojo.websocket.ChatMessage;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务类
 *
 * @Author: haoanxu
 * @Date: 2025/11/17 14:39
 */
// 注解说明：@ServerEndpoint 定义WebSocket端点地址（前端后续要连这个地址）
// {userId} 是路径参数，用于区分不同用户（比如前端传 userId=1001，就会被捕获）
@Component
@ServerEndpoint("/ws/chat/{userId}")
public class WebSocketServer {
    // 存储所有在线的WebSocket连接：key=userId，value=当前连接对象
    // 存储在线用户：key=userId，value=当前连接对象（线程安全）
    private static ConcurrentHashMap<String, WebSocketServer> onlineUsers = new ConcurrentHashMap<>();

    //当前用户的WebSocket会话
    private Session session;

    //当前连接的用户ID
    private String userId;

    /**
     * 连接建立成功时出发（前端发起连接后，这里会触发）
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId){
        this.session = session;
        this.userId = userId;
        //把当前连接存入map（用户上线）
        onlineUsers.put(userId,this);
        System.out.println("用户[" + userId + "]连接成功，当前在线人数：" + onlineUsers.size());
        sendMessage("✅ 连接成功！你是用户[" + userId + "]"); // 给当前用户发欢迎消息
    }

    /**
     * 收到前端消息时触发（前端发消息，后端这里接收）
     * @param message 前端发送的消息
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("用户[" + userId + "]发送消息：" + message);
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
                sendMessage("❌ 你不是发送者[" + fromUserId + "]，不能发送消息！");
                return;
            }
            //给目标用户发消息
            WebSocketServer targetUser = onlineUsers.get(toUserId);
            if (targetUser != null) {
                // 给接收者发消息（包含发送者信息）
                targetUser.sendMessage(String.format("📩 来自用户[%s]的消息：%s", fromUserId, content));
                // 给发送者回“发送成功”确认
                sendMessage(String.format("✅ 消息已发送给用户[%s]：%s", toUserId, content));
            } else {
                // 目标用户不在线
                sendMessage(String.format("❌ 用户[%s]不在线或不存在！", toUserId));
            }
        } catch (Exception e) {
            System.out.println("消息解析失败（请传JSON格式）：" + e.getMessage());
            sendMessage("❌ 消息格式错误！请传JSON：{\"fromUserId\":\"你的ID\",\"toUserId\":\"目标ID\",\"content\":\"消息内容\"}");
        }
    }

    /**
     * 连接关闭时触发（前端断开连接、刷新页面、关闭浏览器时执行）
     */
    @OnClose
    public void onClose() {
        // 把当前连接从map中移除（用户下线）
        onlineUsers.remove(userId);
        System.out.println("用户[" + userId + "]断开连接，当前在线人数：" + onlineUsers.size());
    }

    /**
     * 连接出错时触发（比如网络异常）
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("用户[" + userId + "]连接出错：" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 给当前用户发消息（内部工具方法）
     */
    private void sendMessage(String message) {
        try {
            //getBasicRemote() 是「同步发送」,sendText(...)：是发送 “文本消息” 的方法
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.out.println("给用户[" + userId + "]发消息失败：" + e.getMessage());
        }
    }
    
}
