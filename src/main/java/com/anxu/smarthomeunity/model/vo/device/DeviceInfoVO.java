package com.anxu.smarthomeunity.model.vo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 设备信息VO
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfoVO {
    private Integer deviceId;
    private Integer userId;
    private Integer roomId;
    private String deviceType;
    private String deviceName;
    private Integer deviceStatus;
    private Integer deviceImage;
    private LocalDateTime createTime;
}
