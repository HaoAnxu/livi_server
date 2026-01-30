package com.anxu.livi_model.model.dto.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备信息数据DTO
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfoDTO {
    private Integer familyId;//家庭id
    private Integer userId;
    private Integer roomId;
    private String deviceType;
    private String deviceName;
    private Integer deviceStatus;
    private String deviceImage;
}
