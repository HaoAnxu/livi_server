package com.anxu.smarthomeunity.pojo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用结果实体（成功/失败提示）
 *
 * @Author: haoanxu
 * @Date: 2025/11/18 14:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMsg {
    private String type; // 消息类型（success、error）
    private String msg;
}
