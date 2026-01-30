package com.anxu.livi_model.model.entity.weCommunity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 信息关联表Entity
 * 将用户id，圈子id，消息id，阅读状态关联
 * @Author: haoanxu
 * @Date: 2025/11/21 13:50
 */
@Data
@TableName("pub_user_community_info_connect")
public class ChatInfoRelaEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer msgId;

    private Integer communityId;

    private Integer readStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
