package com.anxu.livi_module_user.util;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * JWT 工具类
 *
 * @Author: haoanxu
 * @Date: 2025/11/20 15:54
 */
@Component
public class JwtUtils {
    //注入要求不能是static，因为static的属性在类加载时就初始化了，而@Value是在Bean初始化时才注入的
    // 签名密钥（通过@Value注入，非static）
    @Value("${jwt.sign-key}")
    private String signKey;

    // 过期时间（通过@Value注入，非static）
    @Value("${jwt.expire}")
    private Long expire;

    /**
     * 生成 JWT Token（核心方法：只传 id 和 username）
     * @param id 用户ID（比如 1、2、3）
     * @param username 用户名（比如 admin、zhangsan）
     * @return 生成的 Token 字符串
     */
    public String generateJwt(Integer id,String username){
        LocalDateTime now = LocalDateTime.now();
        //plusMinutes的作用是在当前时间基础上加上过期时间，得到过期时间
        LocalDateTime expireTime = now.plusMinutes(expire);

        //构建负载
        Map<String,Object> payload = new HashMap<>();
        payload.put(JWTPayload.ISSUED_AT,now);//签发时间
        payload.put(JWTPayload.EXPIRES_AT,expireTime);//过期时间
        payload.put(JWTPayload.NOT_BEFORE,now);//生效时间
        payload.put("id",id);//用户ID
        payload.put("username",username);//用户名

        //用糊涂生成Token
        return JWTUtil.createToken(payload, signKey.getBytes());
    }

    /**
     * 校验 Token 合法性（是否伪造、是否过期、签名是否正确）
     * @param token 待校验的 Token
     * @return true=合法，false=非法/过期
     */
    public boolean validateToken(String token){
        try {
            // Hutool 自动校验：签名、格式、过期时间、生效时间
            JWT jwt = JWTUtil.parseToken(token).setKey(signKey.getBytes());
            return jwt.validate(0); // 0 表示允许的时钟偏差（避免服务器时间差导致误判）
        } catch (Exception e) {
            return false; // 任何异常都视为 Token 无效
        }
    }

    /**
     * 从 Token 中提取用户 ID
     * @param token 合法的 Token
     * @return 用户 ID（Token 无效返回 null）
     */
    public Integer getUserId(String token) {
        try {
            JSONObject payload = getPayload(token);
            return payload.getInt("id"); // 对应生成时的 "id" 字段
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从 Token 中提取用户名
     * @param token 合法的 Token
     * @return 用户名（Token 无效返回 null）
     */
    public String getUsername(String token) {
        try {
            JSONObject payload = getPayload(token);
            return payload.getStr("username"); // 对应生成时的 "username" 字段
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 私有辅助方法：解析 Token 并获取载荷（Payload）
     */
    private JSONObject getPayload(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(signKey.getBytes());
        JSONObject payload = jwt.getPayloads();
        // 移除不需要的标准字段，只保留自定义的 id 和 username
        payload.remove(JWTPayload.ISSUED_AT);
        payload.remove(JWTPayload.EXPIRES_AT);
        payload.remove(JWTPayload.NOT_BEFORE);
        return payload;
    }
}