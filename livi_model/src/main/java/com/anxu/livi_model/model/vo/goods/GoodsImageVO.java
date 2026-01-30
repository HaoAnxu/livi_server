package com.anxu.livi_model.model.vo.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【商品图片VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsImageVO {
    private Integer imageId; // 商品图片ID
    private String imageUrl; // 商品图片URL
}
