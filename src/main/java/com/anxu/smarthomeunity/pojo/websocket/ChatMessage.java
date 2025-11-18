package com.anxu.smarthomeunity.pojo.websocket;

import lombok.Data;

/**
 * WebSocket 消息传输对象
 *
 * @Author: haoanxu
 * @Date: 2025/11/17 14:51
 */
@Data
public class ChatMessage {
    private String fromUserId; // 发送者ID
    private String toUserId;   // 接收者ID
    private String content;    // 消息内容
}
