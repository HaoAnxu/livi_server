package com.anxu.livi.service;

import com.anxu.livi.model.dto.goods.GoodsCommentDTO;
import com.anxu.livi.model.dto.goods.GoodsOrderDTO;
import com.anxu.livi.model.dto.goods.UserOrderDTO;
import com.anxu.livi.model.dto.wePost.PageDTO;
import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.vo.goods.*;
import com.anxu.livi.model.dto.goods.GoodsQueryDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品相关服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/7 13:32
 */
public interface GoodsService {
    //    查询商品列表
    PageResult queryGoods(GoodsQueryDTO goodsQueryDto);
    //    查询单个商品详情
    GoodsDetailVO queryGoodsDetail(Long goodsId);
    //    查询20个热卖商品
    List<GoodsBriefVO> queryHotGoods();
    //    查询单个商品评论列表
    PageResult queryGoodsComment(PageDTO pageDTO);
    //    下单
    String order(GoodsOrderDTO goodsOrderDTO);
    //    根据model，style和goodsId查询价格
    BigDecimal queryPrice(Integer modelId, Integer styleId, Integer goodsId);
    //    根据订单编号查询订单价格
    BigDecimal queryOrderPrice(String orderNo);
    //    支付（简单实现）
    void pay(String orderNo);
    //    查询用户全部订单
    PageResult queryUserOrders(UserOrderDTO userOrderDTO);
    //    评价商品
    int commentOrders(GoodsCommentDTO goodsCommentDTO);
    //    查询订单物流信息
    List<GoodsOrderLogisticsVO> queryLogistics(String orderNo);
}
