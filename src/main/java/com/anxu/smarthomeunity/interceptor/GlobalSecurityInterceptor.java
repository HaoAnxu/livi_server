package com.anxu.smarthomeunity.interceptor;

import cn.hutool.crypto.SecureUtil; // 导入 Hutool 的 SecureUtil
import com.alibaba.fastjson2.JSONObject;
import com.anxu.smarthomeunity.util.CurrentHolder;
import com.anxu.smarthomeunity.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

/**
 * 全局安全拦截器：拦截所有路径
 * 1. 时间戳校验（防重放）
 * 2. 签名校验（防篡改）
 * 3. JWT 校验（/permission/** 路径登录校验）
 *
 * @Author: haoanxu
 * @Date: 2025/11/24 14:58
 */
@Component
@Slf4j
public class GlobalSecurityInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;
    // 时间戳允许的最大偏差（比如 5 分钟，单位：毫秒）
    private static final long TIMESTAMP_MAX_DIFF = 5 * 60 * 1000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 统一响应格式（避免乱码）
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        // 2. 时间戳校验
        String timestamp = request.getHeader("timestamp");
        if (!validateTimestamp(timestamp)) {
            returnError(response, 400, "请求超时（时间戳无效）");
            return false;
        }

        // 3. 签名校验
        String nonce = request.getHeader("nonce"); // 随机字符串（前端每次请求生成）
        String sign = request.getHeader("sign"); // 前端生成的签名
        if (!validateSign(timestamp, nonce, sign)) {
            returnError(response, 400, "签名无效（请求可能被篡改）");
            return false;
        }

        // 4. JWT 校验（只对 /permission/** 路径做，登录后才能访问）
        String requestUrl = request.getRequestURI();
        if (requestUrl.startsWith("/permission/")) {
            String token = request.getHeader("token");
            // JWT 非空校验
            if (!StringUtils.hasLength(token)) {
                returnError(response, 401, "未登录，缺少 Token");
                log.warn("未登录，缺少 Token - 路径: {}", requestUrl);
                return false;
            }
            // JWT 合法性校验（防伪造、防过期）
            if (!jwtUtils.validateToken(token)) {
                returnError(response, 401, "Token 非法或已过期");
                log.warn("Token 非法或已过期 - 路径: {}", requestUrl);
                return false;
            }
            // 提取用户信息存入 ThreadLocal（供后续业务使用）
            Integer userId = jwtUtils.getUserId(token);
            CurrentHolder.setCurrentId(userId);
        }

        // 所有校验通过，放行
        log.info("安全校验通过 - 路径: {}", requestUrl);
        return true;
    }
    // 请求完成后，清空 ThreadLocal（避免内存泄漏）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentHolder.remove();
    }

    /**
     * 时间戳校验：是否在允许的时间偏差内（防重放攻击）
     */
    private boolean validateTimestamp(String timestamp) {
        if (!StringUtils.hasLength(timestamp)) return false;
        try {
            long requestTime = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis();
            // 允许请求时间比当前时间早/晚 5 分钟（避免时区/网络延迟问题）
            return Math.abs(currentTime - requestTime) <= TIMESTAMP_MAX_DIFF;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 签名校验：前端按约定算法生成签名，后端校验（防篡改）
     * 前端生成规则：sign = MD5(timestamp + nonce + signSecret)
     */
    private boolean validateSign(String timestamp,String nonce,String sign){
        if (!StringUtils.hasLength(timestamp) || !StringUtils.hasLength(nonce) || !StringUtils.hasLength(sign)) {
            return false;
        }
        // 后端按相同规则生成签名
        // 签名密钥
        String signSecret = "0611March7";
        String source = timestamp + nonce + signSecret;
        String generatedSign = SecureUtil.md5(source);
        // 对比前端传递的 sign 和后端生成的 sign
        return generatedSign.equalsIgnoreCase(sign);
    }

    /**
     * 统一返回错误响应
     */
    private void returnError(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        //直接生产json字符串返回
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", status);
        jsonObject.put("msg", message);
        writer.write(jsonObject.toString());
        writer.close();
    }

}

