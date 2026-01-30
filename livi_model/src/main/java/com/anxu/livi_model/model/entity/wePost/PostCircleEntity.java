package com.anxu.livi_model.model.entity.wePost;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【圈子Entity】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:55
 */
@Data
@TableName("pub_post_circle")
public class PostCircleEntity {
    @TableId(value = "circle_id", type = IdType.AUTO)
    private Integer circleId; // 圈子唯一ID

    private String circleName; // 圈子名称

    private String circleDesc; // 圈子描述

    private String circleAvatar; // 圈子头像

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间
}
