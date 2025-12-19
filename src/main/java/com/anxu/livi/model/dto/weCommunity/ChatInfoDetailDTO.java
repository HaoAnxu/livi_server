package com.anxu.livi.model.dto.weCommunity;

import com.anxu.livi.model.entity.weCommunity.ChatInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息详情DTO
 *
 * @Author: haoanxu
 * @Date: 2025/12/2 17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatInfoDetailDTO extends ChatInfoEntity {
    private String name; // 发送者用户名
    private String avatar; // 发送者头像
}
