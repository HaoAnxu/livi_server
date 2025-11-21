package com.anxu.smarthomeunity.model.dto.pub.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsComment {
    private Integer commentId;//商品评论id
    private Integer goodsId;//商品id
    private Integer userId;//用户id
    private String commentContent;//评论内容
    private Integer commentScore;//评论分数
    private Integer commentLikeNum;//评论点赞数
    private Integer commentReplyNum;//评论回复数
    private Integer commentStatus;//评论状态-0表示删除，1表示正常
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
