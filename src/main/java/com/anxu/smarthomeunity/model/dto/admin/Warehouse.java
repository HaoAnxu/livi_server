package com.anxu.smarthomeunity.model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {
    private int warehouseId;//仓库id
    private String warehouseName;//仓库名称
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
