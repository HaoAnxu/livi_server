package com.anxu.livi_module_user.mapper.goods;

import com.anxu.livi_model.model.entity.goods.GoodsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * 商品信息Mapper接口
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 11:01
 */
@Mapper
public interface GoodsMapper extends BaseMapper<GoodsEntity> {
    //    更新并重新统计商品评分
    int updateScore();
}
