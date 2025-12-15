package com.anxu.smarthomeunity.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring上下文工具类：用于在非Spring管理的类中获取Bean，或在Service层获取WebSocket层的Bean
 *
 * @Author: haoanxu
 * @Date: 2025/12/15
 */
@Component // 必须加@Component，让Spring扫描并初始化
public class SpringContextUtil implements ApplicationContextAware {

    // 静态变量存储Spring上下文
    private static ApplicationContext applicationContext;

    /**
     * Spring启动时自动调用，注入上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取Spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据类型获取Bean（推荐用这个，类型安全）
     */
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new RuntimeException("Spring上下文未初始化！");
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据名称获取Bean（备用）
     */
    public static Object getBean(String beanName) {
        if (applicationContext == null) {
            throw new RuntimeException("Spring上下文未初始化！");
        }
        return applicationContext.getBean(beanName);
    }
}