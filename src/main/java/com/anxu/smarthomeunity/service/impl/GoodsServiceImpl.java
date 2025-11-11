package com.anxu.smarthomeunity.service.impl;

import com.anxu.smarthomeunity.mapper.GoodsMapper;
import com.anxu.smarthomeunity.pojo.Result.PageResult;
import com.anxu.smarthomeunity.pojo.pub.goods.Goods;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anxu.smarthomeunity.pojo.pub.goods.query.GoodsQuery;
import com.anxu.smarthomeunity.service.GoodsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    //    查询商品列表
    @Override
    public PageResult queryGoods(GoodsQuery goodsQuery) {
        //创建分页对象
        Page<Goods> page = new Page<>(goodsQuery.getPage(), goodsQuery.getPageSize());
        //构建查询条件
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        1.固定条件，只查询上架商品
        queryWrapper.eq("goods_status",1);
//        2.动态条件，商品名称，非空才匹配
        if(goodsQuery.getGoodsName() != null && !goodsQuery.getGoodsName().trim().isEmpty()){
            queryWrapper.like("goods_name",goodsQuery.getGoodsName());
        }
//        3.动态条件，商品类型，非空才匹配
        if(goodsQuery.getGoodsType() != null && !goodsQuery.getGoodsType().trim().isEmpty()){
            queryWrapper.eq("goods_type",goodsQuery.getGoodsType());
        }
//        4.动态排序
        String sortRule = goodsQuery.getSortRule();
        if ("by_price_desc".equals(sortRule)) {
            // 价格升序（便宜优先）：orderByAsc(字段名)
            queryWrapper.orderByAsc("goods_price");
        } else if ("by_price_asc".equals(sortRule)) {
            // 价格降序（贵的优先）：orderByDesc(字段名)
            queryWrapper.orderByDesc("goods_price");
        } else if ("by_sales".equals(sortRule)) {
            // 销量降序
            queryWrapper.orderByDesc("goods_sales");
        } else if ("by_rating".equals(sortRule)) {
            // 评分降序
            queryWrapper.orderByDesc("goods_score");
        } else {
            // 默认：更新时间降序
            queryWrapper.orderByDesc("update_time");
        }
        Page<Goods> resultPage = this.goodsMapper.selectPage(page, queryWrapper);
//        转换类型
        return new PageResult(resultPage.getTotal(),resultPage.getRecords());
    }

     //    更新并重新统计商品评分
    @Override
    public Integer resetScore() {
        int updateProductCount = this.goodsMapper.updateScore();
        if(updateProductCount > 0) {
            log.info("更新商品评分成功，更新商品数量：{}", updateProductCount);
        } else {
            log.info("暂无商品有评论，或所有商品评分已最新，未执行更新");
        }
        return updateProductCount;
    }

    //    查询单个商品详情
    @Override
    public Goods queryGoodsDetail(Long goodsId) {
        log.info("查询商品详情，商品id：{}", goodsId);
        Goods goods = this.goodsMapper.selectById(goodsId);
        if(goods == null) {
            log.info("商品不存在，商品id：{}", goodsId);
        }
        return goods;
    }
}
