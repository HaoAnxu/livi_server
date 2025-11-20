package com.anxu.smarthomeunity.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
/**
 * JWT 工具类
 *
 * @Author: haoanxu
 * @Date: 2025/11/20 15:54
 */
@Component
public class JwtUtils {
    // 签名密钥（通过@Value注入，非static）
    @Value("${jwt.sign-key}")
    private String signKey;

    // 过期时间（通过@Value注入，非static）
    @Value("${jwt.expire}")
    private Long expire;

    /**
     * 生成JWT令牌（实例方法）
     */
    public String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
    }

    /**
     * 解析JWT令牌（实例方法）
     */
    public Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}