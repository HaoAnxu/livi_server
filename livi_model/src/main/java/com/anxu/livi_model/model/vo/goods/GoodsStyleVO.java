package com.anxu.livi_model.model.vo.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【商品款式VO】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsStyleVO {
    private Integer styleId;//商品款式id
    private String goodsStyle;//款式名称
}
