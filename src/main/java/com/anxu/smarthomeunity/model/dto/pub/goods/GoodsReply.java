package com.anxu.smarthomeunity.model.dto.pub.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsReply {
    private Integer replyId;//商品评论回复id
    private Integer commentId;//商品评论id
    private Integer userId;//用户id
    private Integer replyToUserId;//回复人id
    private String replyContent;//商品评论回复内容
    private Integer replyStatus;//回复状态-0表示删除，1表示正常
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
