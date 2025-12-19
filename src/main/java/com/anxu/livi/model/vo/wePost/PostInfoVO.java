package com.anxu.livi.model.vo.wePost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 【帖子信息VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoVO {
    private Integer postId; // 帖子唯一ID

    private Integer circleId; // 圈子ID

    private Integer userId; // 用户ID

    private String postTitle; // 帖子标题

    private String postContent; // 帖子内容

    private String postImage; // 帖子图片

    private LocalDateTime createTime; // 创建时间
}
