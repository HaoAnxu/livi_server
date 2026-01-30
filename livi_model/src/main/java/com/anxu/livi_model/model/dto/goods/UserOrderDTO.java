package com.anxu.livi_model.model.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【用户订单DTO】
 *
 * @Author: haoanxu
 * @Date: 2026/1/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDTO {
    private Integer userId;
    private String sortRule;
    private Integer page;
    private Integer pageSize;
}
