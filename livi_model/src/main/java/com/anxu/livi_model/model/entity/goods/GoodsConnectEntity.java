package com.anxu.livi_model.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 【商品-款式型号关联表】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
@Data
@TableName("pub_goods_connect")
public class GoodsConnectEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer goodsId;//商品id

    private Integer modelId;//型号id

    private Integer styleId;//款式id

    private BigDecimal goodsPrice;//商品价格

    private Integer goodsStock;//库存

    private Boolean isShow;//是否展示-0表示不展示，1表示展示

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
