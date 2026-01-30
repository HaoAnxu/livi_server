package com.anxu.livi_model.model.dto.wePost;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【帖子评论DTO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 15:26
 */
@Data
public class PostCommentDTO {
    private Integer postId; // 帖子ID

    private Integer userId; // 用户ID

    private Integer parentId; // 父评论ID, 0表示根评论

    private Integer toUserId; // 回复用户ID, 0表示不是回复

    private String content; // 评论内容
}
