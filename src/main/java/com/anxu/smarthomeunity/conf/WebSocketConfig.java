package com.anxu.smarthomeunity.conf;

/**
 * WebSocket 配置类
 *
 * @Author: haoanxu
 * @Date: 2025/11/17 14:22
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    // 注入 ServerEndpointExporter，自动注册 @ServerEndpoint 注解的 WebSocket 端点
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
