package com.anxu.livi_module_user.conf;

import com.anxu.livi_module_user.common.interceptor.GlobalSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置类
 * 注册拦截器
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 15:41
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private GlobalSecurityInterceptor globalSecurityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalSecurityInterceptor)
                .addPathPatterns("/**"); // 拦截所有路径，无排除项
    }
}
