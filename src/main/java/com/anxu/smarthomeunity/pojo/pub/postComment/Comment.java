package com.anxu.smarthomeunity.pojo.pub.postComment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer commentId;//帖子评论id
    private Integer userId;//用户id
    private Integer postId;//帖子id
    private String commentContent;//评论内容
    private Integer commentLikeNum;//评论点赞数
    private Integer commentReplyNum;//评论回复数
    private Integer commentStatus;//评论状态(0 正常，1 被删除)
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
