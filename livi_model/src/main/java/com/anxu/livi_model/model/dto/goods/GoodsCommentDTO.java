package com.anxu.livi_model.model.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 【商品评价DTO】
 *
 * @Author: haoanxu
 * @Date: 2026/1/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCommentDTO {
    private Integer orderId;
    private Integer goodsId;//商品id,关联商品表
    private Integer userId;//用户id,关联用户表
    private String commentContent;//评论内容
    private Double commentScore;//评论评分
}
