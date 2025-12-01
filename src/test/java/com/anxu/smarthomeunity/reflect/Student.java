package com.anxu.smarthomeunity.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    // 私有字段
    private String name;
    // 公有字段
    public int age;
    // 静态字段
    public static String school = "北京大学";

    // 私有方法
    private String getInfo() {
        return "姓名：" + name + "，年龄：" + age;
    }
}
