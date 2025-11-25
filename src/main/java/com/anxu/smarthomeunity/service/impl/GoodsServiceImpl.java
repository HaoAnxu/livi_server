package com.anxu.smarthomeunity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.smarthomeunity.mapper.GoodsImageMapper;
import com.anxu.smarthomeunity.mapper.GoodsMapper;
import com.anxu.smarthomeunity.model.Result.PageResult;
import com.anxu.smarthomeunity.model.dto.pub.goods.GoodsDetailDto;
import com.anxu.smarthomeunity.model.entity.goods.GoodsEntity;
import com.anxu.smarthomeunity.model.entity.goods.GoodsImageEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anxu.smarthomeunity.model.dto.pub.goods.query.GoodsQueryDto;
import com.anxu.smarthomeunity.service.GoodsService;

import java.util.List;

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsImageMapper goodsImageMapper;


    //    查询商品列表
    @Override
    public PageResult queryGoods(GoodsQueryDto goodsQueryDto) {
        //创建分页对象
        Page<GoodsEntity> page = new Page<>(goodsQueryDto.getPage(), goodsQueryDto.getPageSize());
        //构建查询条件
        QueryWrapper<GoodsEntity> queryWrapper = new QueryWrapper<>();
//        1.固定条件，只查询上架商品
        queryWrapper.eq("goods_status", 1);
//        2.动态条件，商品名称，非空才匹配
        if (goodsQueryDto.getGoodsName() != null && !goodsQueryDto.getGoodsName().trim().isEmpty()) {
            queryWrapper.like("goods_name", goodsQueryDto.getGoodsName());
        }
//        3.动态条件，商品类型，非空才匹配
        if (goodsQueryDto.getGoodsType() != null && !goodsQueryDto.getGoodsType().trim().isEmpty()) {
            queryWrapper.eq("goods_type", goodsQueryDto.getGoodsType());
        }
//        4.动态排序
        String sortRule = goodsQueryDto.getSortRule();
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
        Page<GoodsEntity> resultPage = this.goodsMapper.selectPage(page, queryWrapper);
        // 转换类型
        return new PageResult(resultPage.getTotal(), resultPage.getRecords());
    }

    //    更新并重新统计商品评分
    @Override
    public Integer resetScore() {
        int updateProductCount = this.goodsMapper.updateScore();

        if (updateProductCount > 0) {
            log.info("更新商品评分成功，更新商品数量：{}", updateProductCount);
        } else {
            log.info("暂无商品有评论，或所有商品评分已最新，未执行更新");
        }
        return updateProductCount;
    }

    //    查询单个商品详情
    @Override
    public GoodsDetailDto queryGoodsDetail(Long goodsId) {
        log.info("查询商品详情，商品id：{}", goodsId);
        //先查详情的基础数据
        GoodsEntity goodsEntity = this.goodsMapper.selectById(goodsId);
        if (goodsEntity == null) {
            log.info("商品不存在，商品id：{}", goodsId);
            return null;
        }
        //将goods转换为goodsDetail
        GoodsDetailDto goodsDetailDto = BeanUtil.copyProperties(goodsEntity, GoodsDetailDto.class);
        //再查图片
        QueryWrapper<GoodsImageEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsId);
        List<GoodsImageEntity> goodsImageEntityList = this.goodsImageMapper.selectList(queryWrapper);
        goodsDetailDto.setGoodsImageEntityList(goodsImageEntityList);
        return goodsDetailDto;
    }
}
