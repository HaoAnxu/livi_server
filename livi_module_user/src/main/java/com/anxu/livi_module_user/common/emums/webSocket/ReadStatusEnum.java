package com.anxu.livi_module_user.common.emums.webSocket;

import lombok.Getter;

/**
 * 读取状态枚举类
 *
 * @Author: haoanxu
 * @Date: 2025/12/3 14:53
 */
@Getter
public enum ReadStatusEnum {
    UNREAD(0,"未读"),
    READ(1,"已读");
    /**
     * 读取状态编码
     */
    private final int code;
    /**
     * 读取状态描述
     */
    private final String info;
    //构造方法
    ReadStatusEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }
    /**
     * 根据编码获取枚举实例
     *
     * @param code 读取状态编码
     * @return 读取状态枚举实例
     */
    public static ReadStatusEnum getByCode(int code){
        for (ReadStatusEnum value : values()) {
            if (value.getCode() == code){
                return value;
            }
        }
        return null;
    }
}
