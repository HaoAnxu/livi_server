package com.anxu.livi.model.vo.goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 【订单物流信息VO】
 *
 * @Author: haoanxu
 * @Date: 2026/1/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrderLogisticsVO {
    private Integer logisticsId;//物流信息ID
    private String orderNo;//订单号,关联订单表
    private Integer LogisticsStatus;
    private String LogisticsInfo;
    private LocalDateTime createTime;
}
