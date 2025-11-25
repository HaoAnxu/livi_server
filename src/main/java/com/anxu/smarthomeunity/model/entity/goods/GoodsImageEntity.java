package com.anxu.smarthomeunity.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 商品图片Entity
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 10:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pub_goods_image")
public class GoodsImageEntity {
    @TableId(value = "image_id",type = IdType.AUTO)
    private Long imageId;//商品图片ID

    private Long goodsId;//商品ID

    private String imageUrl;//商品图片URL

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
