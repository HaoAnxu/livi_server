package com.anxu.smarthomeunity.model.dto.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket结果类
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 13:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketResult {
    private String type;
    private String data;
}
