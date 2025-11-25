package com.anxu.smarthomeunity.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户中心_基础信息查询_请求参数
 *
 * @Author: haoanxu
 * @Date: 2025/11/25 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private int id;//用户id

    private String username;//用户名

    private String email;//邮箱

    private Integer gender;//性别(0 表示男，1 表示女)

    private String signature;//个人签名

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
