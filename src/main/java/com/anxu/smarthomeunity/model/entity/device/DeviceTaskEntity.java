package com.anxu.smarthomeunity.model.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 设备任务Entity
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:47
 */
@Data
@TableName("device_task")
public class DeviceTaskEntity {
    @TableId(value = "task_id", type = IdType.AUTO)
    private Integer taskId;

    private Integer deviceId;

    private Integer userId;

    private String taskType;//once,for,long

    private Integer permit;//0-不允许，1-允许

    private Integer taskStatus;//任务状态:0-终止,1-待执行,2-执行中,3-异常

    private LocalDate onceStartDate;//开始执行的日期(once类型)

    private LocalDate forNextDate;//下次可以执行的日期(for类型)

    private String forModel;//for类型的执行模式:day,week,month

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
