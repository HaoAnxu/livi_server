package com.anxu.smarthomeunity.model.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfo {
    private int id;//管理员id
    private String username;//用户名
    private String password;//口令
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
