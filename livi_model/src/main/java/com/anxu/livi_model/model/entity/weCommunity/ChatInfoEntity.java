package com.anxu.livi_model.model.entity.weCommunity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天消息实体类Entity
 * 记录聊天信息，数据持久化
 * @Author: haoanxu
 * @Date: 2025/11/21 10:42
 */
@Data
@TableName("pub_community_info")
public class ChatInfoEntity {
    @TableId(value = "msg_id", type = IdType.AUTO)
    private Integer msgId; // 消息唯一ID

    private Integer communityId; // 圈子ID

    private Integer fromUserId; // 发送者ID

    private String content; // 消息内容（文本）

    private String msgType = "text"; // 消息类型，默认文本

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
