package com.anxu.livi_model.model.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 【商品订单DTO】
 *
 * @Author: haoanxu
 * @Date: 2026/1/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrderDTO {
    private Integer goodsId;//商品id
    private Integer userId;//用户id
    private String goodsModel;//商品型号
    private String goodsStyle;//商品款式
    private Integer goodsNum;//商品数量
    private String orderAddress;//订单收货地址
    //decimal(10,2)
    private BigDecimal orderPrice;//订单价格
    private Integer orderStatus;//订单状态:0-待支付,1-待发货,2-配送中,3-已签收,4-已取消
}
