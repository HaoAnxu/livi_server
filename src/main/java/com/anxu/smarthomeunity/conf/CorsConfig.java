package com.anxu.smarthomeunity.conf;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @Author: haoanxu
 * @Date: 2025/11/18 09:43
 */
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有接口（包括 /api/*、/admin/* 等）
                .allowedOrigins("http://localhost:5173","http://localhost:80") // 允许 Vue3 前端域名（必须精确，不能带 /）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法（含预检请求 OPTIONS）
                .allowedHeaders("*") // 允许所有请求头（如 Content-Type、Authorization 等）
                .allowCredentials(true) // 允许携带 Cookie（如需前后端传 Cookie 必须开启）
                .maxAge(3600); // 预检请求缓存时间（1小时，减少 OPTIONS 请求次数）
    }
}
