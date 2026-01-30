package com.anxu.livi_model.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【商品款式Entity】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
@Data
@TableName("pub_goods_style")
public class GoodsStyleEntity {
    @TableId(value = "style_id", type = IdType.AUTO)
    private Integer styleId;

    private Integer goodsId;//商品id

    private String goodsStyle;//款式名称

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
