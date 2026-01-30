package com.anxu.livi_model.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【订单物流信息表】
 *
 * @Author: haoanxu
 * @Date: 2026/1/7
 */
@Data
@TableName("pub_goods_order_logistics")
public class GoodsOrderLogisticsEntity {
    @TableId(value = "logistics_id", type = IdType.AUTO)
    private Integer logisticsId;//物流信息ID

    private String orderNo;//订单号,关联订单表

    private Integer LogisticsStatus;

    private String LogisticsInfo;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
