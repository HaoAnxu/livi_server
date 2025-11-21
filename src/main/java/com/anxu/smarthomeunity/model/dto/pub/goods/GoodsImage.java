package com.anxu.smarthomeunity.model.dto.pub.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsImage {
    private Integer imageId;//商品图片id
    private Integer goodsId;//商品id
    private String imageUrl;//商品图片url
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
