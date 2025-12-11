package com.anxu.smarthomeunity.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * 跨域配置
 *
 * @Author: haoanxu
 * @Date: 2025/11/18 09:43
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建跨域配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许前端域名（5173端口）跨域
        config.addAllowedOrigin("http://localhost:5173");
        // 允许携带Cookie（如果需要）
        config.setAllowCredentials(true);
        // 允许所有请求方法（GET/POST/PUT/DELETE等）
        config.addAllowedMethod("*");
        // 允许所有请求头
        config.addAllowedHeader("*");
        // 预检请求有效期（秒），避免频繁发OPTIONS请求
        config.setMaxAge(3600L);

        // 2. 配置跨域规则生效的路径（所有接口）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 3. 返回跨域过滤器
        return new CorsFilter(source);
    }
}
