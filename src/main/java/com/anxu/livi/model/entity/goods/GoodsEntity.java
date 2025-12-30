package com.anxu.livi.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
/**
 * 商品Entity
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 10:58
 */
@Data
@TableName("pub_goods")
public class GoodsEntity {
    @TableId(value = "goods_id",type = IdType.AUTO)
    private Integer goodsId;//商品id,主键,自增

    private String goodsName;//商品名称

    private String goodsType;//商品类型-关联设备类型

    private Double goodsPrice;//商品价格

    private String goodsDept;//商品所属部门

    private String goodsStock;//商品库存

    private Integer goodsSales;//商品销售量

    private String goodsIntro;//商品介绍

    private String goodsThumbnail;//商品缩略图

    private Integer goodsStatus;//商品状态-0表示下架，1表示上架

    private Double goodsScore;//商品评分

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间

}
