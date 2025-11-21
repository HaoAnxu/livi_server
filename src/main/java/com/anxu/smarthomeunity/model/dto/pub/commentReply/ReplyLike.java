package com.anxu.smarthomeunity.model.dto.pub.commentReply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyLike {
    private Integer likeId;//评论回复点赞id
    private Integer replyId;//评论回复id
    private Integer userId;//用户id
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
