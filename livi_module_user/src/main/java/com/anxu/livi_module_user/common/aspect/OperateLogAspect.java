package com.anxu.livi_module_user.common.aspect;

import com.anxu.livi_module_user.common.annotation.OperateLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Spring AOP 实现操作日志记录 切面类
 *
 * @Author: haoanxu
 * @Date: 2025/12/1 14:19
 */
@Slf4j
@Order(0)//指定切面执行顺序，数字越小越先执行（0是最高优先级）
@Aspect
@Component
public class OperateLogAspect {
    //定义切入点，匹配所有标注了@OperateLog注解的方法
    @Pointcut("@annotation(com.anxu.livi.common.annotation.OperateLog)")
    public void operateLogPointcut() {}

    //环绕通知
    @Around("operateLogPointcut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        //1.获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解和注解的属性
        OperateLog operateLog = method.getAnnotation(OperateLog.class);
        String desc = operateLog.value();

        //前置日志
        long startTime = System.currentTimeMillis();
        System.out.println("===== 开始执行：" + method.getName() + "，操作描述：" + desc + " =====");

        Object result = joinPoint.proceed();

        //后置日志
        long endTime = System.currentTimeMillis();
        System.out.println("===== 执行完成：" + method.getName() + "，操作描述：" + desc + "，耗时：" + (endTime - startTime) + "ms =====");

        return result;
    }
}
