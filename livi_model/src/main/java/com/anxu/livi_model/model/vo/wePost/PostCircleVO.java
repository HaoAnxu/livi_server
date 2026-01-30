package com.anxu.livi_model.model.vo.wePost;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 【帖子圈VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCircleVO {
    private Integer circleId; // 圈子唯一ID

    private String circleName; // 圈子名称

    private String circleDesc; // 圈子描述

    private String circleAvatar; // 圈子头像

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
}
