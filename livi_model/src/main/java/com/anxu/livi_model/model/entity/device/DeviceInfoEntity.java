package com.anxu.livi_model.model.entity.device;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备信息Entity
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 11:02
 */
@Data
@TableName("device_info")
public class DeviceInfoEntity {
    @TableId(value = "device_id", type = IdType.AUTO)
    private Integer deviceId;

    private Integer familyId;//家庭id

    private Integer userId;

    private Integer roomId;

    private String deviceType;

    private String deviceName;

    private Integer deviceStatus;

    private String deviceImage;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
