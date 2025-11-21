package com.anxu.smarthomeunity.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pub_goods")
public class Goods {
    @TableId(value = "goods_id",type = IdType.AUTO)
    private Integer goodsId;//商品id,主键,自增

    private String goodsName;//商品名称

    private String goodsType;//商品类型-关联设备类型

    private Double goodsPrice;//商品价格

    private String goodsStock;//商品库存

    private Integer goodsSales;//商品销售量

    private String goodsIntro;//商品介绍

    private Integer goodsStatus;//商品状态-0表示下架，1表示上架

    private Double goodsScore;//商品评分

    private Integer goodsCommentCount;//商品评论数量

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间

}
