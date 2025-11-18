package com.anxu.smarthomeunity.pojo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket 消息传输对象
 *
 * @Author: haoanxu
 * @Date: 2025/11/17 14:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String type;       // 消息类型（如："chat"、"system" 等）
    private String fromUserId; // 发送者ID
    private String toUserId;   // 接收者ID
    private String content;    // 消息内容
}
