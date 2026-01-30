package com.anxu.livi_model.model.vo.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 【商品首页简略信息】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBriefVO {
    private Integer goodsId;//商品id,主键,自增
    private String goodsName;//商品名称
    private BigDecimal goodsPrice;//商品价格
    private BigDecimal goodsOriginalPrice;//商品原价
    private Integer goodsSales;//商品销售量
    private String goodsThumbnail;//商品缩略图
}
