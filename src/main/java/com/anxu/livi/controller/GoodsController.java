package com.anxu.livi.controller;

import com.anxu.livi.common.annotation.OperateLog;
import com.anxu.livi.model.vo.goods.GoodsBriefVO;
import com.anxu.livi.model.vo.goods.GoodsDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.anxu.livi.model.Result.PageResult;
import com.anxu.livi.model.Result.Result;
import com.anxu.livi.model.dto.goods.GoodsQueryDTO;
import com.anxu.livi.service.GoodsService;

import java.util.List;

/**
 * 商品相关接口
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
    @GetMapping("/goods/queryHotGoods")
    public Result queryHotGoods(){
        log.info("查询12个热卖商品");
        List<GoodsBriefVO> goodsBriefVOList = goodsService.queryHotGoods();
        return Result.success(goodsBriefVOList);
    }

    //    查询商品列表
    @OperateLog("查询商品列表")
    @PostMapping("/goods/queryGoods")
    public Result queryGoods(@RequestBody GoodsQueryDTO goodsQueryDto){
        log.info("查询商品信息，参数：{}", goodsQueryDto);
        PageResult pageResult = goodsService.queryGoods(goodsQueryDto);
        return Result.success(pageResult);
    }

    //    更新商品评分和评论数量
    @OperateLog("更新商品评分和评论数量")
    @GetMapping("/goods/resetScore")
    public Result resetScore(){
        log.info("更新商品评分和评论数量");
        Integer updateProductCount = goodsService.resetScore();
        if(updateProductCount > 0) {
            return Result.success(updateProductCount);
        } else {
            return Result.error("更新商品评分和评论数量失败");
        }
    }

    //    查询单个商品详情
    @OperateLog("查询单个商品详情")
    @PostMapping("/goods/queryGoodsDetail")
    public Result queryGoodsDetail(@RequestParam Long goodsId){
        log.info("查询商品详情，参数：{}",goodsId);
        GoodsDetailVO goodsDetailVO = goodsService.queryGoodsDetail(goodsId);
        return Result.success(goodsDetailVO);
    }
}
