package com.anxu.smarthomeunity.mapper;

import com.anxu.smarthomeunity.pojo.pub.goods.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    //    更新并重新统计商品评分
    int updateScore();
}
