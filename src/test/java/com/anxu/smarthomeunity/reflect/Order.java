package com.anxu.smarthomeunity.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @FieldDesc("订单ID")
    private String orderId;
    @FieldDesc("订单金额")
    public Double amount;
    public static Integer MAX_AMOUNT = 10000;
}
