package com.anxu.livi_module_user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.livi_module_user.controller.DeviceTaskServer;
import com.anxu.livi_module_user.mapper.device.DeviceInfoMapper;
import com.anxu.livi_module_user.mapper.device.DeviceTaskMapper;
import com.anxu.livi_model.model.dto.device.DeviceTaskDTO;
import com.anxu.livi_model.model.entity.device.DeviceTaskEntity;
import com.anxu.livi_model.model.vo.device.DeviceTaskVO;
import com.anxu.livi_module_user.service.DeviceTaskService;

import com.anxu.livi_module_user.util.SpringContextUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    @Autowired
    private DeviceTaskServer deviceTaskServer;


    //创建设备执行任务,校验时间段是否已经存在任务
    @Override
    public String createTask(DeviceTaskDTO deviceTaskDTO) {
        log.info("【创建设备任务】设备ID：{}，任务类型：{}", deviceTaskDTO.getDeviceId(), deviceTaskDTO.getTaskType());

        // 基础校验：设备是否存在
        if (deviceInfoMapper.selectById(deviceTaskDTO.getDeviceId()) == null) {
            log.error("【创建设备任务失败】设备ID不存在：{}", deviceTaskDTO.getDeviceId());
            return "设备ID不存在";
        }

        DeviceTaskEntity deviceTask = BeanUtil.copyProperties(deviceTaskDTO, DeviceTaskEntity.class);
        LocalDate nowDate = LocalDate.now();
        String taskType = deviceTaskDTO.getTaskType();

        if ("for".equals(taskType)) {
            // for任务校验：同设备+时间段重叠的有效for任务 → 冲突
            if (checkForTaskTimeOverlap(deviceTaskDTO)) {
                log.error("【创建设备任务失败】设备ID {} 的 {} - {} 时间段已被循环任务占用（天/周/月），禁止创建",
                        deviceTaskDTO.getDeviceId(), deviceTaskDTO.getBeginTime(), deviceTaskDTO.getEndTime());
                return "该设备的该时间段已被循环任务占用，禁止创建";
            }
            deviceTask.setForNextDate(nowDate); // for任务固定当天开始
        } else if ("once".equals(taskType)) {
            // once任务执行日期：默认当天
            LocalDate onceExecDate = deviceTaskDTO.getOnceStartDate() != null ? deviceTaskDTO.getOnceStartDate() : nowDate;
            deviceTask.setOnceStartDate(onceExecDate);

            // once任务校验：1. 时间段被for任务占用 → 冲突；2. 当天同时间段有once任务 → 冲突
            if (checkForTaskTimeOverlap(deviceTaskDTO) || checkOnceTaskTimeOverlap(deviceTaskDTO, onceExecDate)) {
                log.error("【创建设备任务失败】设备ID {} 在 {} {} - {} 时间段已被占用（循环/单次任务）",
                        deviceTaskDTO.getDeviceId(), onceExecDate, deviceTaskDTO.getBeginTime(), deviceTaskDTO.getEndTime());
                return "该设备在所选时间段内已存在执行中的任务（单次/循环），禁止重复创建";
            }
        }

        // 插入任务
        boolean result = deviceTaskMapper.insert(deviceTask) > 0;
        log.info("【创建设备任务完成】任务ID：{}，结果：{}", deviceTask.getTaskId(), result);
        // 推送更新后的任务列表到设备
        if (result) {
            SpringContextUtil.getBean(DeviceTaskServer.class).pushTaskListByDeviceId(deviceTaskDTO.getDeviceId());
        }
        return result ? "success" : "创建任务失败";
    }

    //校验：同设备是否有有效for任务与新任务时间段重叠（for任务永久占用时间段）
    private boolean checkForTaskTimeOverlap(DeviceTaskDTO dto) {
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", dto.getDeviceId())
                .eq("permit", 1) // 仅校验有效任务
                .eq("task_type", "for") // 只查for任务
                // 时间段重叠：新任务begin < 已有end 且 新任务end > 已有begin
                .lt("begin_time", dto.getEndTime())
                .gt("end_time", dto.getBeginTime());

        return deviceTaskMapper.selectCount(queryWrapper) > 0;
    }
    //校验：同设备+指定日期是否有有效once任务与新任务时间段重叠
    private boolean checkOnceTaskTimeOverlap(DeviceTaskDTO dto, LocalDate onceExecDate) {
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", dto.getDeviceId())
                .eq("permit", 1) // 仅校验有效任务
                .eq("task_type", "once") // 只查once任务
                .eq("once_start_date", onceExecDate) // 仅校验指定执行日期
                // 时间段重叠
                .lt("begin_time", dto.getEndTime())
                .gt("end_time", dto.getBeginTime());

        return deviceTaskMapper.selectCount(queryWrapper) > 0;
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
                .lt("end_time", now);
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
//        log.info("【自动停止任务】符合条件任务数：{}", deviceTaskList.size());

        if (deviceTaskList.isEmpty()) {
            return;
        }

        for (DeviceTaskEntity currentItem : deviceTaskList) {
            // 先更新设备状态为0（关闭）
            deviceInfoMapper.updateByDeviceId(currentItem.getDeviceId(), 0);
            pushDeviceStatusToFrontend(currentItem.getDeviceId());
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
        log.info("【自动停止任务完成】更新{}条任务状态", deviceTaskList.size());
        // 推送更新后的任务列表到设备
        if (!deviceTaskList.isEmpty()) {
            Integer deviceId = deviceTaskList.get(0).getDeviceId();
            SpringContextUtil.getBean(DeviceTaskServer.class).pushTaskListByDeviceId(deviceId);
        }
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
//        log.info("【自动启动任务】符合条件任务数：{}", deviceTaskList.size());

        if (deviceTaskList.isEmpty()) {
            return;
        }

        Set<Integer> nonLongDeviceIds = deviceTaskList.stream()
                .filter(task -> "once".equals(task.getTaskType()) || "for".equals(task.getTaskType()))
                .map(DeviceTaskEntity::getDeviceId)
                .collect(Collectors.toSet());
        // 遍历once/for任务的设备ID，终止Long任务
        for (Integer deviceId : nonLongDeviceIds) {
            stopAllLongTask(deviceId);
            log.info("【自动启动任务-前置操作】设备ID：{} 已终止所有执行中的Long任务", deviceId);
        }

        // 2. long任务的设备ID不执行终止操作，直接更新状态
        Set<Integer> longDeviceIds = deviceTaskList.stream()
                .filter(task -> "long".equals(task.getTaskType()))
                .map(DeviceTaskEntity::getDeviceId)
                .collect(Collectors.toSet());

        // 批量更新所有待启动任务的状态为执行中
        for (DeviceTaskEntity task : deviceTaskList) {
            task.setTaskStatus(2);//设置为执行中
            task.setUpdateTime(now);//设置更新时间为当前时间
        }
        deviceTaskMapper.startTaskByTaskId(deviceTaskList);
        // 更新设备状态为执行中（包含所有任务类型的设备）
        Set<Integer> allDeviceIds = deviceTaskList.stream()
                .map(DeviceTaskEntity::getDeviceId)
                .collect(Collectors.toSet());
        allDeviceIds.forEach(deviceId -> {
            deviceInfoMapper.updateByDeviceId(deviceId, 1);
            // ========== 关键新增：推送设备状态到前端 ==========
            pushDeviceStatusToFrontend(deviceId);
        });
        log.info("【自动启动任务完成】更新{}个设备状态为执行中", allDeviceIds.size());

        // 推送更新后的任务列表到设备
        if (!deviceTaskList.isEmpty()) {
            // 获取设备ID（取第一个即可，因为是按设备ID去重的）
            Integer deviceId = deviceTaskList.get(0).getDeviceId();
            // 调用WS的推任务列表方法（需要把DeviceTaskServer注入进来）
            SpringContextUtil.getBean(DeviceTaskServer.class).pushTaskListByDeviceId(deviceId);
        }
    }

    // 终止所有Long类型任务
    private void stopAllLongTask(Integer deviceId) {
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", deviceId)
                .eq("task_type", "long")
                .eq("task_status", 2); // 仅查询执行中的任务
        List<DeviceTaskEntity> taskList = deviceTaskMapper.selectList(queryWrapper);
        if (taskList.isEmpty()) {
            log.warn("【终止所有Long类型任务】设备ID：{}，无执行中任务", deviceId);
            return;
        }
        log.info("【终止所有Long类型任务】设备ID：{}，待处理任务数：{}", deviceId, taskList.size());

        // 定义要批量更新的任务列表（避免循环里逐个更新）
        List<DeviceTaskEntity> updateTaskList = new ArrayList<>();
        for (DeviceTaskEntity task : taskList) {
            if (task.getTaskStatus() == 2) { // 仅处理执行中的任务
                task.setTaskStatus(0);
                task.setPermit(0);
                // 统一设置更新时间
                task.setUpdateTime(LocalDateTime.now());
                // 加入批量更新列表
                updateTaskList.add(task);
            }
        }

        // 批量更新任务状态
        if (!updateTaskList.isEmpty()) {
            deviceTaskMapper.batchUpdateTaskStatus(updateTaskList);
            log.info("【终止所有Long类型任务】设备ID：{}，更新{}条任务状态", deviceId, updateTaskList.size());
        }
        deviceInfoMapper.updateByDeviceId(deviceId, 0);
        log.info("【终止所有Long类型任务】设备ID：{}，状态已更新为关闭", deviceId);
    }

    // 查询设备执行任务记录
    @Override
    public List<DeviceTaskVO> queryTaskRecord(Integer deviceId) {
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", deviceId)
                .orderByDesc("create_time");
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
        log.info("【查询任务记录】设备ID：{}，任务数：{}", deviceId, deviceTaskList.size());

        // 初始化VO列表
        List<DeviceTaskVO> deviceTaskVOList = new ArrayList<>();
        // 遍历Entity列表，逐个转换为VO
        for (DeviceTaskEntity entity : deviceTaskList) {
            DeviceTaskVO vo = new DeviceTaskVO();
            //拷贝Entity属性到VO（字段名+类型一致时自动映射）
            BeanUtils.copyProperties(entity, vo);
            deviceTaskVOList.add(vo);
        }
        return deviceTaskVOList;
    }

    // 手动停止设备执行中的任务
    @Override
    public boolean stopDeviceRunningTask(Integer deviceId) {
        // 检查设备是否有执行中的任务
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", deviceId)
                .eq("task_status", 2); // 仅查询执行中的任务
        List<DeviceTaskEntity> taskList = deviceTaskMapper.selectList(queryWrapper);

        if (taskList.isEmpty()) {
            log.warn("【手动停止任务】设备ID：{}，无执行中任务", deviceId);
            return false;
        }
        log.info("【手动停止任务】设备ID：{}，待处理任务数：{}", deviceId, taskList.size());

        // 定义要批量更新的任务列表（避免循环里逐个更新）
        List<DeviceTaskEntity> updateTaskList = new ArrayList<>();
        for (DeviceTaskEntity task : taskList) {
            if (task.getTaskStatus() == 2) { // 仅处理执行中的任务
                switch (task.getTaskType()) {
                    // long类型：终止+禁止执行
                    case "long":
                        task.setTaskStatus(0);
                        task.setPermit(0);
                        break;
                    // for类型：重置为待执行，更新下次执行时间
                    case "for":
                        task.setTaskStatus(1);
                        task.setPermit(1);
                        switch (task.getForModel()) {
                            case "day":
                                task.setForNextDate(task.getForNextDate().plusDays(1));
                                break;
                            case "week":
                                task.setForNextDate(task.getForNextDate().plusWeeks(1));
                                break;
                            case "month":
                                task.setForNextDate(task.getForNextDate().plusMonths(1));
                                break;
                        }
                        break;
                    // once类型：仅终止+禁止执行，不置空onceStartDate（保留记录）
                    case "once":
                        task.setTaskStatus(0);
                        task.setPermit(0);
                        break;
                }
                // 统一设置更新时间
                task.setUpdateTime(LocalDateTime.now());
                // 加入批量更新列表
                updateTaskList.add(task);
            }
        }

        // 批量更新任务状态
        if (!updateTaskList.isEmpty()) {
            deviceTaskMapper.batchUpdateTaskStatus(updateTaskList);
            log.info("【手动停止任务】设备ID：{}，更新{}条任务状态", deviceId, updateTaskList.size());
        }

        // 同步更新设备状态为「关闭」（0）
        deviceInfoMapper.updateByDeviceId(deviceId, 0);
        log.info("【手动停止任务完成】设备ID：{}，状态已更新为关闭", deviceId);
        // 推送更新后的任务列表到设备
        if (!updateTaskList.isEmpty()) {
            SpringContextUtil.getBean(DeviceTaskServer.class).pushTaskListByDeviceId(deviceId);
        }
        return true;
    }

    //根据任务类型查询设备执行任务记录
    @Override
    public List<DeviceTaskVO> queryTaskByTaskType(Integer deviceId, String taskType) {
        QueryWrapper<DeviceTaskEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", deviceId)
                .eq("task_type", taskType)
                .orderByDesc("create_time");
        List<DeviceTaskEntity> deviceTaskList = deviceTaskMapper.selectList(queryWrapper);
        log.info("【查询任务记录】设备ID：{}，任务类型：{}，任务数：{}", deviceId, taskType, deviceTaskList.size());

        // 初始化VO列表
        List<DeviceTaskVO> deviceTaskVOList = new ArrayList<>();
        // 遍历Entity列表，逐个转换为VO
        for (DeviceTaskEntity entity : deviceTaskList) {
            DeviceTaskVO vo = new DeviceTaskVO();
            //拷贝Entity属性到VO（字段名+类型一致时自动映射）
            BeanUtils.copyProperties(entity, vo);
            deviceTaskVOList.add(vo);
        }
        return deviceTaskVOList;
    }

    //根据任务ID删除设备执行任务记录
    @Override
    public boolean deleteTask(Integer taskId) {
        // 检查任务是否存在
        DeviceTaskEntity task = deviceTaskMapper.selectById(taskId);
        if (task == null) {
            log.warn("【删除任务根据taskId】任务ID：{} 不存在", taskId);
            return false;
        }
        // 删除任务
        deviceTaskMapper.deleteById(taskId);
        log.info("【删除任务根据taskId】成功删除任务ID：{}", taskId);
        return true;
    }

    // 推送设备状态到前端
    private void pushDeviceStatusToFrontend(Integer deviceId) {
        try {
            DeviceTaskServer deviceTaskServer = SpringContextUtil.getBean(DeviceTaskServer.class);
            // 获取该设备的WebSocket会话
            WebSocketSession session = DeviceTaskServer.device_instance_map.get(deviceId);
            if (session != null && session.isOpen()) {
                deviceTaskServer.pushDeviceStatus(session, deviceId);
            } else {
                log.warn("【推送设备状态失败】设备ID：{} 无有效WebSocket连接", deviceId);
            }
        } catch (Exception e) {
            log.error("【推送设备状态异常】设备ID：{}", deviceId, e);
        }
    }
}