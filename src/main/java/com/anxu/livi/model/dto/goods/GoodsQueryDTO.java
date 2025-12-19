package com.anxu.livi.model.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 商品查询数据DTO
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsQueryDTO {
    private String goodsName;
    private String goodsType;
    private String sortRule;
    private Integer page = 1;
    private Integer pageSize = 12;
}
