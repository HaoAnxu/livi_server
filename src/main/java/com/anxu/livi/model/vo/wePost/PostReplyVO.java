package com.anxu.livi.model.vo.wePost;

import lombok.Data;

/**
 * 【回复帖子评论VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/24
 */
@Data
public class PostReplyVO {
    private Integer toUserId; // 回复用户ID, 0表示不是回复
    private String toUserName; // 回复用户名
    private String replyContent;
}
