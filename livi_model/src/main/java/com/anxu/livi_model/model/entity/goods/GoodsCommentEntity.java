package com.anxu.livi_model.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品评论Entity
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 11:00
 */
@Data
@TableName("pub_goods_comment")
public class GoodsCommentEntity {
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;//商品评论ID

    private Integer goodsId;//商品id,关联商品表

    private Integer userId;//用户id,关联用户表

    private String commentContent;//评论内容

    private Double commentScore;//评论评分

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
