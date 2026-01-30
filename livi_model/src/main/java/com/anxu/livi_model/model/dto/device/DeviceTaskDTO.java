package com.anxu.livi_model.model.dto.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 设备任务DTO
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTaskDTO {
    private Integer deviceId;
    private Integer userId;
    private Integer permit;//0-不允许，1-允许
    private String taskType;//once,for,long
    private LocalDate onceStartDate;//开始执行的日期(once类型)
    private String forModel;//for类型的执行模式:day,week,month
    private LocalTime beginTime;
    private LocalTime endTime;
}
