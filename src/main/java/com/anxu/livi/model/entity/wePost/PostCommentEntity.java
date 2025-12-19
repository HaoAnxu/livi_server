package com.anxu.livi.model.entity.wePost;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【评论Entity】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:56
 */
@Data
@TableName("pub_post_comment")
public class PostCommentEntity {
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId; // 评论唯一ID

    private Integer postId; // 帖子ID

    private Integer userId; // 用户ID

    private String commentContent; // 评论内容

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间
}

