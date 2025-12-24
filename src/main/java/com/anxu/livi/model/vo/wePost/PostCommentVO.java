package com.anxu.livi.model.vo.wePost;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private String userName; // 用户名

    private String userAvatar; // 用户头像

    private String content; // 评论内容

    private Integer parentId; // 父评论ID, 0表示根评论

    private Integer toUserId; // 回复用户ID, 0表示不是回复

    private String toUserName; // 回复用户名

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
}
