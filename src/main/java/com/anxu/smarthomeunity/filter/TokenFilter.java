package com.anxu.smarthomeunity.filter;

import com.anxu.smarthomeunity.util.CurrentHolder;
import com.anxu.smarthomeunity.util.JwtStaticProxy;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
/**
 * 令牌过滤器
 *
 * @Author: haoanxu
 * @Date: 2025/11/20 15:48
 */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    //过滤器初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    //过滤器执行方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //类型转换
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取请求的url
        String url = request.getRequestURI();

        //判断请求是否包含login或者register，如果包含，说明是登录操作，放行\
        if(!url.contains("/permission")){
            log.info("用户执行的操作不是权限管理界面，放行");
            filterChain.doFilter(request,response);
            return;
        }
        //获取请求头中的令牌
        //令牌在请求头的原因：登录成功系统下发token令牌，作为参数传递给前端，前端将token令牌存储到localStorage中，而在前端的request拦截器中会将token令牌存储到请求头中
        String jwt = request.getHeader("token");

        //判断令牌是否存在，如果不存在说明没登录
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头中没有token令牌，返回未登录的结果");
            response.setStatus(401);
            //输出一下状态码
            log.info("状态码：{}",response.getStatus());
            return;
        }

        //解析token令牌，如果解析失败，说明令牌无效
        try{
            Claims claims = JwtStaticProxy.parseJWT(jwt);
            //从claims中获取ID
            Integer userId = (Integer) claims.get("id");
            //将ID存储到当前线程的ThreadLocal中
            CurrentHolder.setCurrentId(userId);
        }catch (Exception e){
            log.info("解析token令牌失败，返回未登录的结果");
            response.setStatus(401);
            return;
        }

        //放行
        log.info("令牌合法，放行");
        filterChain.doFilter(request,response);

        //清空当前线程的ThreadLocal中的数据
        CurrentHolder.remove();
    }

    //过滤器销毁方法
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
