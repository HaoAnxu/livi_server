package com.anxu.livi_model.model.dto.wePost;

import lombok.Data;

/**
 * 【帖子信息DTO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19
 */
@Data
public class PostInfoDTO {
    private Integer circleId; // 圈子ID

    private Integer userId; // 用户ID

    private String postTitle; // 帖子标题

    private String postContent; // 帖子内容

    private String postImage; // 帖子图片
}
