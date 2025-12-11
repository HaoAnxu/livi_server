package com.anxu.smarthomeunity.model.vo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 设备任务VO
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceTaskVO {
    private Integer taskId;
    private Integer deviceId;
    private Integer userId;
    private String taskType;//once,for,long
    private Integer permit;//0-不允许，1-允许
    private Integer taskStatus;//任务状态:0-终止,1-待执行,2-执行中,3-异常
    private String forModel;//for类型的执行模式:day,week,month
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
