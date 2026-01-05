package com.anxu.livi.common.emums.goods;

public enum OrderStatusEnum {
    PENDING_PAY("pending_pay", 0, "待支付"),
    PENDING_SHIP("pending_ship", 1, "待发货"),
    SHIPPING("shipping", 2, "配送中"),
    SIGNED("signed", 3, "已签收"),
    CANCELLED("cancelled", 4, "已取消");

    private final String sortRule; // 前端传参值
    private final Integer statusCode; // 数据库状态码
    private final String desc; // 描述

    OrderStatusEnum(String sortRule, Integer statusCode, String desc) {
        this.sortRule = sortRule;
        this.statusCode = statusCode;
        this.desc = desc;
    }

    // 根据前端sortRule获取对应的状态码
    public static Integer getStatusCodeBySortRule(String sortRule) {
        for (OrderStatusEnum enumObj : OrderStatusEnum.values()) {
            if (enumObj.sortRule.equals(sortRule)) {
                return enumObj.statusCode;
            }
        }
        return null; // 无匹配值
    }
}