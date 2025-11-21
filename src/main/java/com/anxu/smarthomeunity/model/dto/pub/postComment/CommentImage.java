package com.anxu.smarthomeunity.model.dto.pub.postComment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentImage {
    private Integer imageId;//评论配图id
    private Integer commentId;//评论id
    private String imageUrl;//图片url
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
