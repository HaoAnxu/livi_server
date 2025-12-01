package com.anxu.smarthomeunity.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @FieldDesc("用户ID") // 自定义注解：字段描述
    private Long id;
    @FieldDesc("用户名")
    public String username;
    private String password; // 无注解
    public static String DEFAULT_AVATAR = "default.png";

    public User(long l, String zhangsan) {
        this.id = l;
        this.username = zhangsan;
    }

    private String encryptPassword(String pwd) {
        return pwd + "_md5"; // 模拟密码加密
    }
}

