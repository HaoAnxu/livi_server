package com.anxu.smarthomeunity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.smarthomeunity.common.emums.Device.DeviceTaskTypeEnum;
import com.anxu.smarthomeunity.mapper.device.DeviceInfoMapper;
import com.anxu.smarthomeunity.mapper.device.DeviceTaskMapper;
import com.anxu.smarthomeunity.model.dto.device.DeviceTaskDTO;
import com.anxu.smarthomeunity.model.entity.device.DeviceTaskEntity;
import com.anxu.smarthomeunity.model.vo.device.DeviceTaskVO;
import com.anxu.smarthomeunity.service.DeviceTaskService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 设备定时任务服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 15:40
 */
@Slf4j
@Service
public class DeviceTaskServiceImpl implements DeviceTaskService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    private DeviceTaskMapper deviceTaskMapper;

    //创建设备执行任务-单条
    @Override
    public boolean createTask(DeviceTaskDTO deviceTaskDTO) {
        log.info("创建设备任务：{}", deviceTaskDTO);
        LocalDate nowDate = LocalDate.now();
        if (deviceInfoMapper.selectById(deviceTaskDTO.getDeviceId()) == null) {
            log.error("创建设备任务失败，设备ID不存在：{}", deviceTaskDTO.getDeviceId());
            return false;
        }

        DeviceTaskEntity deviceTask = BeanUtil.copyProperties(deviceTaskDTO, DeviceTaskEntity.class);
        if (deviceTaskDTO.getTaskType().equals("once")) {
            deviceTask.setOnceStartDate(nowDate);
        } else if (deviceTaskDTO.getTaskType().equals("for")) {
            deviceTask.setForNextDate(nowDate);
        }
        boolean result = deviceTaskMapper.insert(deviceTask) > 0;
        log.info("创建设备任务结束，任务ID：{}，结果：{}", deviceTask.getTaskId(), result);
        return result;
    }

    //停止到时间的定时任务
    @Async("taskExecutor")
    @Override
    public void stopDeviceTask(LocalDateTime now) {
        //查询所有符合条件的任务
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("task_type", "once", "for")
                .eq("task_status", 2)
                .eq("permit", 1)
                .lt("end_time", now);//lt的意思是小于等于now
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
        log.info("【停止任务】查询到符合条件的任务数：{}，任务列表：{}", deviceTaskList.size(), deviceTaskList);

        if (deviceTaskList.isEmpty()) {
            return;
        }

        for (DeviceTaskEntity currentItem : deviceTaskList) {
            // 先更新设备状态为0（关闭）
            deviceInfoMapper.updateByDeviceId(currentItem.getDeviceId(), 0);
            // 再更新任务状态（for重置为1，once终止为0）
            switch (currentItem.getTaskType()) {
                case "for":
                    currentItem.setTaskStatus(1); // 重置为待执行，次日仍可触发
                    //根据forModel更新下次执行时间
                    switch (currentItem.getForModel()) {
                        case "day":
                            currentItem.setForNextDate(currentItem.getForNextDate().plusDays(1));
                            break;
                        case "week":
                            currentItem.setForNextDate(currentItem.getForNextDate().plusWeeks(1));
                            break;
                        case "month":
                            currentItem.setForNextDate(currentItem.getForNextDate().plusMonths(1));
                            break;
                    }
                    break;
                case "once":
                    currentItem.setTaskStatus(0); // 终止
                    currentItem.setPermit(0);     // 禁止执行，避免重复触发
                    break;
            }
        }
        // 批量更新
        deviceTaskMapper.stopTaskByTaskId(deviceTaskList);
        log.info("【停止任务】批量更新{}条任务状态完成", deviceTaskList.size());
    }

    //启动到时间的定时任务
    @Async("taskExecutor")
    @Override
    public void startDeviceTask(LocalDateTime now) {
        //筛选任务，统一条件
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("task_type", "once", "for", "long")
                .eq("task_status", 1)
                .eq("permit", 1);
        //分组
        queryWrapper.and(wrapper -> wrapper
                .nested(nested -> nested
                        .eq("task_type", "once")
                        .le("begin_time", now.toLocalTime())
                        .ge("end_time", now.toLocalTime())
                        //once类型需要满足执行日期，某一天的某个时间段执行任务
                        .eq("once_start_date", now.toLocalDate())
                )
                .or()
                .nested(nested -> nested
                        .eq("task_type", "for")
                        .le("begin_time", now.toLocalTime())
                        .ge("end_time", now.toLocalTime())
                        //for类型需要满足下次可以执行的日期，例如每周执行，每天执行
                        .eq("for_next_date", now.toLocalDate())
                )
                .or()
                .nested(nested -> nested
                        .eq("task_type", "long")
                        .le("begin_time", now.toLocalTime())
                )
        );
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
        log.info("【启动任务】查询到符合条件的任务数：{}，任务列表：{}", deviceTaskList.size(), deviceTaskList);

        if (deviceTaskList.isEmpty()) {
            return;
        }

        // 批量更新任务状态（保持原有逻辑）
        for (DeviceTaskEntity task : deviceTaskList) {
            task.setTaskStatus(2);//设置为执行中
            task.setUpdateTime(now);//设置更新时间为当前时间
        }
        deviceTaskMapper.startTaskByTaskId(deviceTaskList);

        //更新设备状态
        deviceTaskList.stream()
                .map(DeviceTaskEntity::getDeviceId)
                .distinct()
                .forEach(deviceId -> {
                    deviceInfoMapper.updateByDeviceId(deviceId, 1); // 设备状态置为执行中
                });
        log.info("【启动任务】所有设备状态更新完成，总计更新{}个设备",
                deviceTaskList.stream().map(DeviceTaskEntity::getDeviceId).distinct().count());
    }

    @Override
    public List<DeviceTaskVO> queryTaskRecord(Integer deviceId) {
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", deviceId)
                .orderByDesc("create_time");
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
        log.info("【查询任务记录】查询到符合条件的任务数：{}，任务列表：{}", deviceTaskList.size(), deviceTaskList);
        // 初始化VO列表
        List<DeviceTaskVO> deviceTaskVOList = new ArrayList<>();
        // 遍历Entity列表，逐个转换为VO
        for (DeviceTaskEntity entity : deviceTaskList) {
            DeviceTaskVO vo = new DeviceTaskVO();
            // 核心：拷贝Entity属性到VO（字段名+类型一致时自动映射）
            BeanUtils.copyProperties(entity, vo);
            deviceTaskVOList.add(vo);
        }
        return deviceTaskVOList;
    }

    @Override
    public boolean stopDeviceTask(Integer taskId) {
        // 检查任务是否存在
        DeviceTaskEntity task = deviceTaskMapper.selectById(taskId);
        if (task == null) {
            log.warn("任务ID {} 不存在", taskId);
            return false;
        }
        // 更新任务状态
        task.setTaskStatus(0); // 终止
        task.setPermit(0);     // 禁止执行，避免重复触发
        task.setUpdateTime(LocalDateTime.now()); // 设置更新时间为当前时间
        // 执行更新操作
        int updated = deviceTaskMapper.updateById(task);
        if (updated > 0) {
            log.info("【停止任务】成功更新任务ID {} 状态为终止", taskId);
            return true;
        } else {
            log.error("【停止任务】更新任务ID {} 状态为终止失败", taskId);
            return false;
        }
    }

    @Override
    public boolean stopDeviceLongTask() {
        // 检查任务是否存在
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_type", "long")
                .orderByDesc("create_time")
                .last("limit 1");
        DeviceTaskEntity task = deviceTaskMapper.selectOne(queryWrapper);
        if (task == null) {
            log.warn("最新的long类型任务不存在");
            return false;
        }
        // 更新任务状态
        task.setTaskStatus(0); // 终止
        task.setPermit(0);     // 禁止执行，避免重复触发
        task.setUpdateTime(LocalDateTime.now()); // 设置更新时间为当前时间
        // 执行更新操作
        int updated = deviceTaskMapper.updateById(task);
        if (updated > 0) {
            log.info("成功更新最新的long类型任务ID {} 状态为终止", task.getTaskId());
            return true;
        } else {
            log.error("更新最新的long类型任务ID {} 状态为终止失败", task.getTaskId());
            return false;
        }
    }
}