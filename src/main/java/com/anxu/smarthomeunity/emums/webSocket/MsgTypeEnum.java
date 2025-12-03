package com.anxu.smarthomeunity.emums.webSocket;

import lombok.Getter;

/**
 * 消息类型枚举类
 *
 * @Author: haoanxu
 * @Date: 2025/12/3 14:51
 */
@Getter
public enum MsgTypeEnum {
    //心跳相关
    PING("ping","心跳请求"),
    PONG("pong","心跳响应"),
    //历史记录相关
    REQUEST_CHAT_HISTORY("request_chat_history","查询聊天记录"),
    CHAT_HISTORY_DATA("chat_history_data","聊天记录数据"),
    //消息推送相关
    REALTIME("realtime","实时消息"),
    OFFLINE("offline","离线消息"),
    //错误相关
    ERROR("error","错误消息");
    /**
     * 消息类型编码
     */
    private final String code;
    /**
     * 消息类型描述
     */
    private final String info;
    //构造方法
    MsgTypeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }
    /**
     * 根据编码获取枚举实例
     *
     * @param code 消息类型编码
     * @return 消息类型枚举实例
     */
    public static MsgTypeEnum getByCode(String code){
        for (MsgTypeEnum value : values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
