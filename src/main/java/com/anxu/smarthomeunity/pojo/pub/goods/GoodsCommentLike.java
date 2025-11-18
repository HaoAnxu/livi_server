package com.anxu.smarthomeunity.pojo.pub.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCommentLike {
    private Integer likeId;//商品评论点赞id
    private Integer commentId;//商品评论id
    private Integer userId;//用户id
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
