package com.anxu.livi.model.entity.goods;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 【商品订单Entity】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
@Data
@TableName("pub_goods_order")
public class GoodsOrderEntity {
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    private String orderNo;//订单编号

    private Integer goodsId;//商品id

    private Integer userId;//用户id

    private Integer goodsModel;//商品型号

    private Integer goodsStyle;//商品款式

    private Integer goodsNum;//商品数量

    private String orderAddress;//订单收货地址

    //decimal(10,2)
    private BigDecimal orderPrice;//订单价格

    private Integer orderStatus;//订单状态:0-待支付,1-待发货,2-配送中,3-已签收,4-已取消

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
