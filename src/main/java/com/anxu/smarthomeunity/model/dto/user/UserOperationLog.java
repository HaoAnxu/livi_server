package com.anxu.smarthomeunity.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOperationLog {
    private int dolId;//操作日志id
    private int deviceId;//设备id
    private int userId;//用户id
    private String operationInfo;//操作信息
    private int operationStatus;//操作状态(0-失败 1-成功)
    private LocalDateTime beginTime;//开始时间
    private LocalDateTime endTime;//结束时间
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
