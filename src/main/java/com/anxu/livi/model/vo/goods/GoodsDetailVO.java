package com.anxu.livi.model.vo.goods;

import com.anxu.livi.model.entity.goods.GoodsCommentEntity;
import com.anxu.livi.model.entity.goods.GoodsImageEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 单个商品详情DTO (详情，图片列表，评论列表)
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 10:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVO {
    private Integer goodsId;//商品id,主键,自增
    private String goodsName;//商品名称
    private String goodsType;//商品类型-关联设备类型
    private Double goodsPrice;//商品价格
    private String goodsStock;//商品库存
    private Integer goodsSales;//商品销售量
    private String goodsIntro;//商品介绍
    private String goodsThumbnail;//商品缩略图
    private Integer goodsStatus;//商品状态-0表示下架，1表示上架
    private Double goodsScore;//商品评分
    private String goodsDept;//商品所属部门
    private List<GoodsImageVO> goodsImageVOList;//商品图片列表
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
}
