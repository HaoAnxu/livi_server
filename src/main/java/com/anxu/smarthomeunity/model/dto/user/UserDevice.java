package com.anxu.smarthomeunity.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDevice {
    private int deviceId;//设备id
    private String deviceType;//设备类型
    private String deviceName;//设备名称
    private int deviceStatus;//设备状态(0 关闭，1 执行中，2 异常，3 等待执行)
    private int roomId;//房间id
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
