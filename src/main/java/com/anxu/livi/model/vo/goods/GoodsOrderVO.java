package com.anxu.livi.model.vo.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 【商品订单VO】
 *
 * @Author: haoanxu
 * @Date: 2026/1/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrderVO {
    private Integer orderId;
    private String orderNo;//订单编号
    private Integer goodsId;//商品id
    private GoodsDetailVO goodsDetailVO;//商品详情
    private Integer userId;//用户id
    private Integer goodsModel;//商品型号ID
    private String goodsModelName;//商品型号名称
    private Integer goodsStyle;//商品款式ID
    private String goodsStyleName;//商品款式名称
    private Integer goodsNum;//商品数量
    private String orderAddress;//订单收货地址
    //decimal(10,2)
    private BigDecimal orderPrice;//订单价格
    private Integer orderStatus;//订单状态:0-待支付,1-待发货,2-配送中,3-已签收,4-已取消
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
}
