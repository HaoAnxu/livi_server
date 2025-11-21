package com.anxu.smarthomeunity.service;

import com.anxu.smarthomeunity.model.dto.Result.PageResult;
import com.anxu.smarthomeunity.model.entity.goods.Goods;
import com.anxu.smarthomeunity.model.dto.pub.goods.query.GoodsQuery;

public interface GoodsService {
    //    查询商品列表
    PageResult queryGoods(GoodsQuery goodsQuery);
    //    更新并重新统计商品评分
    Integer resetScore();
    //    查询单个商品详情
    Goods queryGoodsDetail(Long goodsId);
}
