package com.anxu.smarthomeunity.conf;

import com.anxu.smarthomeunity.controller.DeviceTaskServer;
import com.anxu.smarthomeunity.controller.WeCommunityServer;
import com.anxu.smarthomeunity.common.interceptor.WebSocketHandshakeSecurityInterceptor;
import com.anxu.smarthomeunity.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
/**
 * WebSocket配置类
 * 注册拦截器
 *
 * @Author: haoanxu
 * @Date: 2025/11/27 17:27
 */
@Configuration
@EnableWebSocket // 开启Spring WebSocket功能（必须加，否则配置不生效）
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private WeCommunityServer weCommunityServer;
    @Autowired
    private DeviceTaskServer  deviceTaskServer;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 1. 注册WS处理器：指定“访问路径”+“处理器类”（替代原生@ServerEndpoint("/ws/xxx")）
        registry.addHandler(weCommunityServer, "/ws/community/{communityId}/{userId}")
                // 2. 跨域配置：允许前端5173端口访问
                .setAllowedOrigins("http://localhost:5173")
                // 3. 绑定握手拦截器-注意，这里是new，不会被Spring容器管理，所以不能@Autowired注入
                .addInterceptors(new WebSocketHandshakeSecurityInterceptor(jwtUtils));
        //device的WebSocket处理器
        registry.addHandler(deviceTaskServer, "/ws/device/{deviceId}/{userId}")
                .setAllowedOrigins("http://localhost:5173")
                .addInterceptors(new WebSocketHandshakeSecurityInterceptor(jwtUtils));
    }
}