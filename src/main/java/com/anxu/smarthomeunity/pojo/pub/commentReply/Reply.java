package com.anxu.smarthomeunity.pojo.pub.commentReply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private Integer replyId;//评论回复id
    private Integer commentId;//评论id
    private Integer userId;//用户id
    private Integer replyToUserId;//回复用户id
    private String replyContent;//回复内容
    private Integer replyStatus;//回复状态(0 表示正常，1 表示删除)
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
