package com.anxu.smarthomeunity.pojo.pub.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCommentImage {
    private Integer imageId;//商品评论图片id
    private Integer commentId;//商品评论id
    private String imageUrl;//商品评论图片url
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
