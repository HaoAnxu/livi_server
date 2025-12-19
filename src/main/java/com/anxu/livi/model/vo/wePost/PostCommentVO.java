package com.anxu.livi.model.vo.wePost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 【帖子评论VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentVO {
    private Integer commentId; // 评论唯一ID

    private Integer postId; // 帖子ID

    private Integer userId; // 用户ID

    private String commentContent; // 评论内容

    private LocalDateTime createTime; // 创建时间
}
