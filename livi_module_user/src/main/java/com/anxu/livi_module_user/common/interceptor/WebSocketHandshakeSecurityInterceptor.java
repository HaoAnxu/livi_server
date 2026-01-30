package com.anxu.livi_module_user.common.interceptor;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSONObject;
import com.anxu.livi_module_user.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * WebSocket握手安全拦截器
 * 兼容：聊天室（/ws/community）、设备控制（/ws/device）的WebSocket握手校验
 *
 * @Author: haoanxu
 * @Date: 2025/11/27 17:22
 */
@Component
@Slf4j
public class WebSocketHandshakeSecurityInterceptor implements HandshakeInterceptor {
    // 时间戳最大允许差值（5分钟）
    private static final long TIMESTAMP_MAX_DIFF = 5 * 60 * 1000;
    // 签名密钥（需和前端保持一致）
    private static final String SIGN_SECRET = "0611March7";

    //使用构造函数注入Jwt工具类
    private final JwtUtils jwtUtils;

    public WebSocketHandshakeSecurityInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * 握手前置拦截逻辑（核心方法）
     * 兼容：聊天室/设备控制两种WebSocket路径
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 转换为Servlet请求对象，获取HTTP请求上下文
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpServletRequest req = servletRequest.getServletRequest();

        // ====================== 通用安全校验（聊天室/设备控制都需要） ======================
        // 1. 时间戳校验
        String timestamp = req.getParameter("timestamp");
        if (!validateTimestamp(timestamp)) {
            log.error("WS握手失败：时间戳无效 | 路径：{}", req.getRequestURI());
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "请求超时（时间戳无效）");
            return false;
        }

        // 2. 签名校验
        String nonce = req.getParameter("nonce");
        String sign = req.getParameter("sign");
        if (!validateSign(timestamp, nonce, sign)) {
            log.error("WS握手失败：签名无效 | 路径：{}", req.getRequestURI());
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "签名无效（请求可能被篡改）");
            return false;
        }

        // 3. Token校验
        String token = req.getParameter("token");
        if (!StringUtils.hasLength(token) || !jwtUtils.validateToken(token)) {
            log.error("WS握手失败：Token无效/过期 | 路径：{}", req.getRequestURI());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            writeErrorResponse(response, 401, "未登录或Token过期");
            return false;
        }
        Integer tokenUserId = jwtUtils.getUserId(token); // 解析Token中的用户ID

        // ====================== 路径解析（区分聊天室/设备控制） ======================
        String requestUri = req.getRequestURI();
        String[] pathParts = requestUri.split("/");
        // 合法路径格式：/ws/xxx/{id1}/{id2} → 拆分后长度至少为5（["", "ws", "community/device", "id1", "id2"]）
        if (pathParts.length < 5) {
            log.error("WS握手失败：路径格式错误 | URI={}，拆分后长度={}", requestUri, pathParts.length);
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "路径格式错误，合法格式：/ws/community/{communityId}/{userId} 或 /ws/device/{deviceId}/{userId}");
            return false;
        }

        // 识别WebSocket类型（community/device）
        String wsType = pathParts[2];
        Integer businessId = null; // 通用变量：communityId/deviceId
        Integer pathUserId = null; // 路径中的userId
        try {
            businessId = Integer.parseInt(pathParts[3]); // 第4段：communityId/deviceId
            pathUserId = Integer.parseInt(pathParts[4]); // 第5段：userId
        } catch (NumberFormatException e) {
            log.error("WS握手失败：路径参数解析失败 | URI={}，参数段3={}，参数段4={}", requestUri, pathParts[3], pathParts[4], e);
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "业务ID/用户ID必须为数字");
            return false;
        }

        // ====================== 权限校验（Token用户ID和路径用户ID一致） ======================
        if (!tokenUserId.equals(pathUserId)) {
            log.error("WS握手失败：权限不足 | Token用户ID={}，路径用户ID={}，路径={}", tokenUserId, pathUserId, requestUri);
            response.setStatusCode(HttpStatus.FORBIDDEN);
            writeErrorResponse(response, 403, "无权限访问该资源");
            return false;
        }

        // ====================== 存入握手属性（供各自的WebSocket处理器使用） ======================
        if ("community".equals(wsType)) {
            // 聊天室：存入communityId + userId
            attributes.put("communityId", businessId);
            attributes.put("userId", pathUserId);
            log.info("WS握手校验通过：聊天室 | 用户[{}] 社区[{}]", pathUserId, businessId);
        } else if ("device".equals(wsType)) {
            // 设备控制：存入deviceId + userId
            attributes.put("deviceId", businessId);
            attributes.put("userId", pathUserId);
            log.info("WS握手校验通过：设备控制 | 用户[{}] 设备[{}]", pathUserId, businessId);
        } else {
            // 未知类型，拒绝
            log.error("WS握手失败：未知的WebSocket类型 | 类型={}，路径={}", wsType, requestUri);
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            writeErrorResponse(response, 400, "未知的WebSocket类型，仅支持community/device");
            return false;
        }

        return true; // 允许建立WebSocket连接
    }

    /**
     * 握手后置处理（无需业务逻辑）
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        if (exception != null) {
            log.error("WS握手完成后出现异常 | 路径：{}", ((ServletServerHttpRequest) request).getServletRequest().getRequestURI(), exception);
        }
    }

    // ====================== 校验时间戳和签名 ======================
    private boolean validateTimestamp(String timestamp) {
        if (!StringUtils.hasLength(timestamp)) return false;
        try {
            long requestTime = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis();
            return Math.abs(currentTime - requestTime) <= TIMESTAMP_MAX_DIFF;
        } catch (NumberFormatException e) {
            log.error("时间戳格式错误，timestamp={}", timestamp, e);
            return false;
        }
    }

    // ====================== 校验签名 ======================
    private boolean validateSign(String timestamp, String nonce, String sign) {
        if (!StringUtils.hasLength(timestamp) || !StringUtils.hasLength(nonce) || !StringUtils.hasLength(sign)) {
            return false;
        }
        String source = timestamp + nonce + SIGN_SECRET;
        String generatedSign = SecureUtil.md5(source);
        return generatedSign.equalsIgnoreCase(sign);
    }

    // ====================== 写入错误响应 ======================
    private void writeErrorResponse(ServerHttpResponse response, int status, String message) throws IOException {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", status);
        jsonObject.put("msg", message);
        try (PrintWriter writer = new PrintWriter(response.getBody())) {
            writer.write(jsonObject.toString());
            writer.flush();
        }
    }
}