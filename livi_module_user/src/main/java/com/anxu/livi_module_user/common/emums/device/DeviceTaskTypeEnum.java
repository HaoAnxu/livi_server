package com.anxu.livi_module_user.common.emums.device;

import lombok.Getter;

/**
 * 设备任务类型枚举
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 15:21
 */
@Getter
public enum DeviceTaskTypeEnum {
    //一次性任务定时任务
    ONCE("once","一次性任务定时任务"),
    //周期性定时任务
    FOR("for","周期性定时任务"),
    //长效任务
    LONG("long","长效任务");

    private final String code;
    private final String info;

    DeviceTaskTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }
    /**
     * 根据编码获取枚举实例
     *
     * @param code 消息类型编码
     * @return 消息类型枚举实例
     */
    public static DeviceTaskTypeEnum getByCode(String code){
        for (DeviceTaskTypeEnum value : values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
