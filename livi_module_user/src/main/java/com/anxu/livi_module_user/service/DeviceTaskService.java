package com.anxu.livi_module_user.service;

import com.anxu.livi_model.model.dto.device.DeviceTaskDTO;
import com.anxu.livi_model.model.vo.device.DeviceTaskVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备定时任务服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 15:39
 */
public interface DeviceTaskService {
    //创建设备定时任务
    String createTask(DeviceTaskDTO deviceTaskDTO);

    //停止到时间的定时任务
    void stopDeviceTask(LocalDateTime now);

    //启动到时间的定时任务
    void startDeviceTask(LocalDateTime now);

    //查询设备执行任务记录
    List<DeviceTaskVO> queryTaskRecord(Integer deviceId);

    //手动关闭
    boolean stopDeviceRunningTask(Integer deviceId);

    //根据任务类型查询设备执行任务记录
    List<DeviceTaskVO> queryTaskByTaskType(Integer deviceId, String taskType);

    //根据任务ID删除设备执行任务记录
    boolean deleteTask(Integer taskId);
}
