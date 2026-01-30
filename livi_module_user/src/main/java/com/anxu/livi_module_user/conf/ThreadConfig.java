package com.anxu.livi_module_user.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * 线程池配置类
 *
 * @Author: haoanxu
 * @Date: 2025/11/25 10:57
 */
// 开启异步方法支持,让@Async注解生效
@EnableAsync
//启用配置属性类
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
@Configuration
@Slf4j
public class ThreadConfig {
    //注入配置属性类-构造方法注入
    private final ThreadPoolConfigProperties pool;
    public ThreadConfig(ThreadPoolConfigProperties pool) {
        this.pool = pool;
    }

    //创建第一个线程池：Spring封装的 ThreadPoolTaskExecutor（@Async专用）
    @Bean("taskExecutor")//给线程池起个名字，默认是方法名，后续@Async("taskExecutor")就能用
    public ThreadPoolTaskExecutor taskExecutor(){
        //创建线程池对象
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //给线程池设参数（从pool对象里拿，也就是从.yml里读的）
        executor.setCorePoolSize(pool.getCoreSize());          // 核心线程数
        executor.setMaxPoolSize(pool.getMaxSize());            // 最大线程数
        executor.setQueueCapacity(pool.getQueueCapacity());    // 任务队列容量
        executor.setKeepAliveSeconds(pool.getKeepAliveTime()); // 空闲线程存活时间（秒）
        executor.setThreadNamePrefix(pool.getPoolPrefixName());// 线程名称前缀（日志里能看到）
        //设置拒绝策略（核心业务用CallerRunsPolicy，避免任务丢失
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置任务完成后是否等待线程池关闭（true：等待所有任务完成再关闭，避免任务中断）
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //初始化线程池（必须调用，否则线程池创建失败）
        executor.initialize();
        //返回线程池对象，Spring会管理
        return executor;
    }

    //创建第二个线程池：JDK原生 ThreadPoolExecutor(手动提交任务)
    @Bean("CompareTaskExecutor")
    public ThreadPoolExecutor compareTaskExecutor(){
        return new ThreadPoolExecutor(
                pool.getCoreSize(),          // 核心线程数
                pool.getMaxSize(),           // 最大线程数
                pool.getKeepAliveTime(),     // 空闲线程存活时间
                TimeUnit.SECONDS,            // 时间单位（和上面的秒对应）
                new LinkedBlockingDeque<>(pool.getQueueCapacity()), // 任务队列（容量从.yml读）
                Executors.defaultThreadFactory(), // 线程工厂（默认就行，不用改）
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );
    }

}
