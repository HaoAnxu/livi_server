package com.anxu.livi.controller;

import com.anxu.livi.common.annotation.OperateLog;
import com.anxu.livi.model.dto.goods.GoodsCommentDTO;
import com.anxu.livi.model.dto.goods.GoodsOrderDTO;
import com.anxu.livi.model.dto.goods.UserOrderDTO;
import com.anxu.livi.model.dto.wePost.PageDTO;
import com.anxu.livi.model.vo.goods.GoodsBriefVO;
import com.anxu.livi.model.vo.goods.GoodsDetailVO;
import com.anxu.livi.model.vo.goods.GoodsOrderLogisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.result.Result;
import com.anxu.livi.model.dto.goods.GoodsQueryDTO;
import com.anxu.livi.service.GoodsService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 【商品相关接口】
 *
 * @Author: haoanxu
 * @Date: 2025/11/7 13:32
 */
@Slf4j
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //首页方法-查询20个热卖商品
    @OperateLog("查询20个热卖商品")
    @GetMapping("/goods/queryHotGoods")
    public Result queryHotGoods() {
        log.info("查询12个热卖商品");
        List<GoodsBriefVO> goodsBriefVOList = goodsService.queryHotGoods();
        return Result.success(goodsBriefVOList);
    }

    //    查询商品列表
    @OperateLog("查询商品列表")
    @PostMapping("/goods/queryGoods")
    public Result queryGoods(@RequestBody GoodsQueryDTO goodsQueryDto) {
        log.info("查询商品信息，参数：{}", goodsQueryDto);
        PageResult pageResult = goodsService.queryGoods(goodsQueryDto);
        return Result.success(pageResult);
    }

    //    查询单个商品详情
    @OperateLog("查询单个商品详情")
    @PostMapping("/permission/goods/queryGoodsDetail")
    public Result queryGoodsDetail(@RequestParam Long goodsId) {
        log.info("查询商品详情，参数：{}", goodsId);
        GoodsDetailVO goodsDetailVO = goodsService.queryGoodsDetail(goodsId);
        return Result.success(goodsDetailVO);
    }

    // 查询单个商品评论列表
    @OperateLog("查询单个商品评论列表")
    @PostMapping("/permission/goods/queryGoodsComment")
    public Result queryGoodsComment(@RequestBody PageDTO pageDTO) {
        log.info("查询商品评论列表，参数：{}", pageDTO);
        PageResult pageResult = goodsService.queryGoodsComment(pageDTO);
        return Result.success(pageResult);
    }

    // 根据model，style和goodsId查询价格,可以为空
    @OperateLog("根据model，style和goodsId查询价格")
    @PostMapping("/permission/goods/queryPrice")
    public Result queryPrice(@RequestParam(required = false) Integer modelId, @RequestParam(required = false) Integer styleId, @RequestParam Integer goodsId) {
        log.info("根据model，style和goodsId查询价格，参数：modelId={}, styleId={}, goodsId={}", modelId, styleId, goodsId);
        BigDecimal price = goodsService.queryPrice(modelId, styleId, goodsId);
        return Result.success(price);
    }

    // 下单
    @OperateLog("下单")
    @PostMapping("/permission/goods/createOrder")
    public Result order(@RequestBody GoodsOrderDTO goodsOrderDTO) {
        log.info("下单，参数：{}", goodsOrderDTO);
        String orderNo = goodsService.order(goodsOrderDTO);
        return Result.success(orderNo);
    }

    // 根据订单编号查询订单价格
    @OperateLog("根据订单编号查询订单价格")
    @PostMapping("/permission/goods/queryOrderPrice")
    public Result queryOrderPrice(@RequestParam String orderNo) {
        log.info("根据订单编号查询订单价格，参数：{}", orderNo);
        BigDecimal price = goodsService.queryOrderPrice(orderNo);
        return Result.success(price);
    }

    // 支付（简单实现）
    @OperateLog("支付")
    @PostMapping("/permission/goods/pay")
    public Result pay(@RequestParam String orderNo) {
        log.info("支付，参数：{}", orderNo);
        goodsService.pay(orderNo);
        return Result.success("支付成功");
    }

    // 分类查询用户全部订单
    @OperateLog("查询用户全部订单")
    @PostMapping("/permission/goods/queryUserOrders")
    public Result queryUserOrders(@RequestBody UserOrderDTO userOrderDTO) {
        log.info("查询用户全部订单，参数：{}", userOrderDTO);
        PageResult pageResult = goodsService.queryUserOrders(userOrderDTO);
        return Result.success(pageResult);
    }

    // 评价商品
    @PostMapping("/permission/goods/commentOrders")
    public Result commentOrders(@RequestBody GoodsCommentDTO goodsCommentDTO) {
        log.info("评价商品，参数: {}", goodsCommentDTO);
        int code = goodsService.commentOrders(goodsCommentDTO);
        if (code != -1) {
            return Result.success();
        } else {
            return Result.error("评价失败");
        }
    }

    //查询订单物流信息
    @PostMapping("/permission/goods/queryLogistics")
    public Result queryLogistics(@RequestParam String orderNo) {
        log.info("查询订单物流信息，参数: {}", orderNo);
        List<GoodsOrderLogisticsVO> goodsOrderLogisticsVOList = goodsService.queryLogistics(orderNo);
        return Result.success(goodsOrderLogisticsVOList);
    }
}
