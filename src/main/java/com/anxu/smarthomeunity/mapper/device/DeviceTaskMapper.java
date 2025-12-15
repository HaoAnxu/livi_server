package com.anxu.smarthomeunity.mapper.device;

import com.anxu.smarthomeunity.model.entity.device.DeviceTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备任务Mapper
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 13:57
 */
@Mapper
public interface DeviceTaskMapper extends BaseMapper<DeviceTaskEntity> {
    //批量停止设备任务
    void stopTaskByTaskId(List<DeviceTaskEntity> deviceTaskEntities);
    //批量启动设备任务
    void startTaskByTaskId(List<DeviceTaskEntity> deviceTaskEntities);
    //批量更新任务状态
    void batchUpdateTaskStatus(List<DeviceTaskEntity> updateTaskList);
}
