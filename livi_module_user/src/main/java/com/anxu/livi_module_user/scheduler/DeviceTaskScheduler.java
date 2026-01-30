package com.anxu.livi_module_user.scheduler;

import com.anxu.livi_module_user.service.DeviceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 设备定时任务调度器
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 14:45
 */
@Slf4j
@Component
public class DeviceTaskScheduler {
    @Autowired
    private DeviceTaskService deviceTaskService;

    //配置轮询时间间隔为10秒
    @Scheduled(fixedRate = 10000)
    public void pollDeviceTask(){
        LocalDateTime now = LocalDateTime.now();
        try {
            //子流程1：停止到时间的定时任务
            deviceTaskService.stopDeviceTask(now);
            //子流程2：启动到时间的定时任务
            deviceTaskService.startDeviceTask(now);
        } catch (Exception e) {
            log.error("设备任务轮询执行异常", e);
        }
    }
}
