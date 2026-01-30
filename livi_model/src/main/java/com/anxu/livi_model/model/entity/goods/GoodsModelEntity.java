package com.anxu.livi_model.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【商品型号Entity】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
@Data
@TableName("pub_goods_model")
public class GoodsModelEntity {
    @TableId(value = "model_id", type = IdType.AUTO)
    private Integer modelId;

    private Integer goodsId;//商品id

    private String goodsModel;//商品型号

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
