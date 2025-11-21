package com.anxu.smarthomeunity.model.dto.pub.goods.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsQuery {
    private String goodsName;
    private String goodsType;
    private String sortRule;
    private Integer page = 1;
    private Integer pageSize = 10;
}
