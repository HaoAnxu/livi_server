package com.anxu.smarthomeunity.model.dto.pub.postComment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLike {
    private Integer likeId;//评论点赞id
    private Integer commentId;//评论id
    private Integer userId;//用户id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
