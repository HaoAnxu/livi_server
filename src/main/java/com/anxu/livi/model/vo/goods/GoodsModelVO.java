package com.anxu.livi.model.vo.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【商品型号VO】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsModelVO {
    private Integer modelId;//商品型号id
    private String goodsModel;//商品型号名称
}
