package com.anxu.livi.service;

import com.anxu.livi.model.result.PageResult;
import com.anxu.livi.model.vo.goods.GoodsBriefVO;
import com.anxu.livi.model.vo.goods.GoodsDetailVO;
import com.anxu.livi.model.dto.goods.GoodsQueryDTO;

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
    //    更新并重新统计商品评分
    Integer resetScore();
    //    查询单个商品详情
    GoodsDetailVO queryGoodsDetail(Long goodsId);
    //    查询20个热卖商品
    List<GoodsBriefVO> queryHotGoods();
}
