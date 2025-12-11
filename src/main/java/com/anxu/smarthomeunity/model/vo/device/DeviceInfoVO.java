package com.anxu.smarthomeunity.model.vo.device;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String familyName;//家庭名称
    private String userName;
    private String roomName;
    private String deviceType;
    private String deviceName;
    private Integer deviceStatus;
    private String deviceImage;
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private LocalDateTime createTime;
}
