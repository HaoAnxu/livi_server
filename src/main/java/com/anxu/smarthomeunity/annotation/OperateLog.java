package com.anxu.smarthomeunity.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @Author: haoanxu
 * @Date: 2025/12/1 14:26
 */
@Target(ElementType.METHOD)
//运行时保留，在运行时可以通过反射获取注解信息
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    String value() default "";
}
