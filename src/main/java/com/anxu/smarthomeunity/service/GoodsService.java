package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.Result.PageResult;
import com.anxu.smarthomeunity.model.dto.pub.goods.GoodsDetailDto;
import com.anxu.smarthomeunity.model.dto.pub.goods.query.GoodsQueryDto;

public interface GoodsService {
    //    查询商品列表
    PageResult queryGoods(GoodsQueryDto goodsQueryDto);
    //    更新并重新统计商品评分
    Integer resetScore();
    //    查询单个商品详情
    GoodsDetailDto queryGoodsDetail(Long goodsId);
}
