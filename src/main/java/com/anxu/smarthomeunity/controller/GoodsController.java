package com.anxu.smarthomeunity.controller;

import com.anxu.smarthomeunity.model.dto.pub.goods.GoodsDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.anxu.smarthomeunity.model.Result.PageResult;
import com.anxu.smarthomeunity.model.Result.Result;
import com.anxu.smarthomeunity.model.dto.pub.goods.query.GoodsQueryDto;
import com.anxu.smarthomeunity.service.GoodsService;

@Slf4j
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //    查询商品列表
    @PostMapping("/goods/queryGoods")
    public Result queryGoods(@RequestBody GoodsQueryDto goodsQueryDto){
        log.info("查询商品信息，参数：{}", goodsQueryDto);
        PageResult pageResult = goodsService.queryGoods(goodsQueryDto);
        return Result.success(pageResult);
    }

    //    更新商品评分和评论数量
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
    @PostMapping("/goods/queryGoodsDetail")
    public Result queryGoodsDetail(@RequestParam Long goodsId){
        log.info("查询商品详情，参数：{}",goodsId);
        GoodsDetailDto goodsDetailDto = goodsService.queryGoodsDetail(goodsId);
        return Result.success(goodsDetailDto);
    }
}
