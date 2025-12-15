package com.anxu.smarthomeunity.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.anxu.smarthomeunity.model.Result.WebSocketResult;
import com.anxu.smarthomeunity.model.dto.device.DeviceTaskDTO;
import com.anxu.smarthomeunity.model.vo.device.DeviceInfoVO;
import com.anxu.smarthomeunity.model.vo.device.DeviceTaskVO;
import com.anxu.smarthomeunity.service.DeviceService;
import com.anxu.smarthomeunity.service.DeviceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DeviceControl—WebSocket接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/15 13:23
 */
@Component
@Slf4j
public class DeviceTaskServer extends TextWebSocketHandler {

    //存储连接实例
    private static final ConcurrentHashMap<Integer, WebSocketSession> user_instance_map = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, WebSocketSession> device_instance_map = new ConcurrentHashMap<>();

    @Autowired
    private DeviceTaskService deviceTaskService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 连接建立成功时触发（替代原@OnOpen注解）
     * 从握手拦截器的属性中获取communityId和userId
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //从握手拦截器的属性中获取deviceId和userId
        Integer deviceId = (Integer) session.getAttributes().get("deviceId");
        Integer userId = (Integer) session.getAttributes().get("userId");

        log.info("用户{} 成功连接WebSocket设备{}", userId, deviceId);

        device_instance_map.put(deviceId, session);
        user_instance_map.put(userId, session);

        //推送最新设备状态给用户
        pushDeviceStatus(session, deviceId);
    }

    /**
     * 收到前端消息时触发（替代原@OnMessage注解）
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        String message = textMessage.getPayload();
        try {
            // 处理心跳
            if ("ping".equals(message)) {
                sendMessage(session, "pong");
                return;
            }
            // 解析前端json
            JSONObject jsonObject = JSON.parseObject(message);
            String msgType = jsonObject.getString("msgType");
            Integer deviceId = jsonObject.getInteger("deviceId");
            Integer userId = (Integer) session.getAttributes().get("userId");

            // 根据msgType执行不同的任务
            switch (msgType) {
                case "check_task": // 保留原有逻辑
                    LocalDateTime now = LocalDateTime.now();
                    deviceTaskService.stopDeviceTask(now);
                    deviceTaskService.startDeviceTask(now);
                    pushDeviceStatus(session, deviceId);
                    break;

                case "change_status":
                    Integer deviceStatus = jsonObject.getInteger("deviceStatus");
                    boolean changeResult = deviceService.changeStatus(deviceId, deviceStatus);

                    // 如果是开启设备（status=1），自动创建长效任务
                    if (deviceStatus == 1) {
                        // 构造长效任务DTO
                        DeviceTaskDTO longTaskDTO = new DeviceTaskDTO();
                        longTaskDTO.setDeviceId(deviceId);
                        longTaskDTO.setUserId(userId);
                        longTaskDTO.setPermit(1);
                        longTaskDTO.setTaskType("long");
                        // 获取当前时间作为开始时间
                        longTaskDTO.setBeginTime(LocalDateTime.now().toLocalTime());

                        // 调用创建任务方法
                        String createResult = deviceTaskService.createTask(longTaskDTO);
                        if ("success".equals(createResult)) {
                            log.info("【WS创建长效任务成功】设备ID：{}，用户ID：{}", deviceId, userId);
                        } else {
                            log.error("【WS创建长效任务失败】设备ID：{}，原因：{}", deviceId, createResult);
                        }
                    }
                    // 如果是关闭设备（status=0），自动终止任务
                    if (deviceStatus == 0) {
                        boolean stopResult = deviceTaskService.stopDeviceRunningTask(deviceId);
                        if (stopResult) {
                            log.info("【WS关闭开关终止任务成功】设备ID：{}", deviceId);
                        } else {
                            log.warn("【WS关闭开关终止任务失败】设备ID：{}，无执行中任务", deviceId);
                        }
                    }

                    String msg = changeResult ?
                            (deviceStatus == 1 ? "状态更改成功，已创建长效任务" : "状态更改成功，已终止所有任务") :
                            "状态更改失败";
                    WebSocketResult result = new WebSocketResult(
                            changeResult ? "success" : "error",
                            msg
                    );
                    sendMessage(session, JSON.toJSONString(result));
                    pushDeviceStatus(session, deviceId);
                    break;

                case "stop_task": // 保留原有逻辑
                    boolean stopResult = deviceTaskService.stopDeviceRunningTask(deviceId);
                    WebSocketResult stopMsg = new WebSocketResult(
                            stopResult ? "success" : "error",
                            stopResult ? "任务停止成功" : "任务停止失败"
                    );
                    sendMessage(session, JSON.toJSONString(stopMsg));
                    pushDeviceStatus(session, deviceId);
                    break;

                // 创建长效任务指令
                case "create_long_task":
                    DeviceTaskDTO longTaskDTO = new DeviceTaskDTO();
                    longTaskDTO.setDeviceId(deviceId);
                    longTaskDTO.setUserId(userId);
                    longTaskDTO.setPermit(1);
                    longTaskDTO.setTaskType("long");
                    longTaskDTO.setBeginTime(LocalDateTime.now().toLocalTime());

                    String createResult = deviceTaskService.createTask(longTaskDTO);
                    WebSocketResult createMsg = new WebSocketResult(
                            "success".equals(createResult) ? "success" : "error",
                            "success".equals(createResult) ? "长效任务创建成功" : createResult
                    );
                    sendMessage(session, JSON.toJSONString(createMsg));
                    pushDeviceStatus(session, deviceId);
                    break;
                case "create_once_task":
                    DeviceTaskDTO onceTaskDTO = new DeviceTaskDTO();
                    onceTaskDTO.setDeviceId(deviceId);
                    onceTaskDTO.setUserId(userId);
                    onceTaskDTO.setPermit(1);
                    onceTaskDTO.setTaskType("once");

                    // 修复：String转LocalDate（前端传的是字符串，如"2025-12-15"）
                    String onceStartDateStr = jsonObject.getString("onceStartDate");
                    if (onceStartDateStr != null && !onceStartDateStr.isEmpty()) {
                        onceTaskDTO.setOnceStartDate(LocalDate.parse(onceStartDateStr));
                    }

                    // 修复：String转LocalTime（前端传的是时间字符串，如"16:30:00"）
                    String beginTimeStr = jsonObject.getString("beginTime");
                    if (beginTimeStr != null && !beginTimeStr.isEmpty()) {
                        onceTaskDTO.setBeginTime(LocalTime.parse(beginTimeStr));
                    }
                    String endTimeStr = jsonObject.getString("endTime");
                    if (endTimeStr != null && !endTimeStr.isEmpty()) {
                        onceTaskDTO.setEndTime(LocalTime.parse(endTimeStr));
                    }

                    String onceCreateResult = deviceTaskService.createTask(onceTaskDTO);
                    WebSocketResult onceCreateMsg = new WebSocketResult(
                            "success".equals(onceCreateResult) ? "success" : "error",
                            "success".equals(onceCreateResult) ? "一次性任务创建成功" : onceCreateResult
                    );
                    sendMessage(session, JSON.toJSONString(onceCreateMsg));
                    pushDeviceStatus(session, deviceId);
                    break;

                case "create_for_task":
                    DeviceTaskDTO forTaskDTO = new DeviceTaskDTO();
                    forTaskDTO.setDeviceId(deviceId);
                    forTaskDTO.setUserId(userId);
                    forTaskDTO.setPermit(1);
                    forTaskDTO.setTaskType("for");
                    forTaskDTO.setForModel(jsonObject.getString("forModel"));

                    // 修复：String转LocalTime
                    String forBeginTimeStr = jsonObject.getString("beginTime");
                    if (forBeginTimeStr != null && !forBeginTimeStr.isEmpty()) {
                        forTaskDTO.setBeginTime(LocalTime.parse(forBeginTimeStr));
                    }
                    String forEndTimeStr = jsonObject.getString("endTime");
                    if (forEndTimeStr != null && !forEndTimeStr.isEmpty()) {
                        forTaskDTO.setEndTime(LocalTime.parse(forEndTimeStr));
                    }

                    String forCreateResult = deviceTaskService.createTask(forTaskDTO);
                    WebSocketResult forCreateMsg = new WebSocketResult(
                            "success".equals(forCreateResult) ? "success" : "error",
                            "success".equals(forCreateResult) ? "循环任务创建成功" : forCreateResult
                    );
                    sendMessage(session, JSON.toJSONString(forCreateMsg));
                    pushDeviceStatus(session, deviceId);
                    break;

                default:
                    log.warn("未知的设备WebSocket指令 | 类型：{} | 内容：{}", msgType, message);
                    sendMessage(session, JSON.toJSONString(new WebSocketResult("error", "未知指令")));
            }
        } catch (Exception e) {
            // 原有异常逻辑
            Integer userId = (Integer) session.getAttributes().get("userId");
            Integer deviceId = (Integer) session.getAttributes().get("deviceId");
            log.error("设备WebSocket消息处理异常 | 用户ID：{} | 设备ID：{}", userId, deviceId, e);
            sendMessage(session, JSON.toJSONString(new WebSocketResult("error", "指令处理失败：" + e.getMessage())));
        }
    }


    /**
     * 连接关闭时触发（替代原@OnClose注解）
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Integer deviceId = (Integer) session.getAttributes().get("deviceId");
        Integer userId = (Integer) session.getAttributes().get("userId");

        // 清理会话存储
        user_instance_map.remove(userId);
        device_instance_map.remove(deviceId);

        log.info("设备WebSocket连接关闭 | 用户ID：{} | 设备ID：{} | 状态：{}", userId, deviceId, status.getReason());
    }

    //工具方法
    //同步发送消息
    private void sendMessage(WebSocketSession session, String message) {
        try {
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            Integer userId = session != null ? (Integer) session.getAttributes().get("userId") : null;
            Integer deviceId = session != null ? (Integer) session.getAttributes().get("deviceId") : null;
            log.error("设备WebSocket消息发送失败 | 用户ID：{} | 设备ID：{}", userId, deviceId, e);
        }
    }

    // 推送状态后同时推任务列表
    public void pushDeviceStatus(WebSocketSession session, Integer deviceId) {
        try {
            DeviceInfoVO deviceInfo = deviceService.queryMyDevice(deviceId);
            WebSocketResult statusResult = new WebSocketResult(
                    "device_status",
                    JSON.toJSONString(deviceInfo)
            );
            sendMessage(session, JSON.toJSONString(statusResult));

            // 推送任务列表
            pushTaskListToFrontend(session, deviceId);

            log.debug("设备状态推送成功 | 设备ID：{} | 状态：{}", deviceId, deviceInfo.getDeviceStatus());
        } catch (Exception e) {
            log.error("设备状态推送失败 | 设备ID：{}", deviceId, e);
            sendMessage(session, JSON.toJSONString(new WebSocketResult("error", "状态推送失败")));
        }
    }

    // 推送任务列表给前端
    private void pushTaskListToFrontend(WebSocketSession session, Integer deviceId) {
        try {
            // 查询该设备的任务列表
            List<DeviceTaskVO> taskList = deviceTaskService.queryTaskRecord(deviceId);
            WebSocketResult taskResult = new WebSocketResult(
                    "task_list", // 新增消息类型：任务列表
                    JSON.toJSONString(taskList)
            );
            sendMessage(session, JSON.toJSONString(taskResult));
            log.debug("【任务列表推送成功】设备ID：{}，任务数：{}", deviceId, taskList.size());
        } catch (Exception e) {
            log.error("【任务列表推送失败】设备ID：{}", deviceId, e);
            sendMessage(session, JSON.toJSONString(new WebSocketResult("error", "任务列表推送失败")));
        }
    }

    //主动推送设备任务列表到前端
    public void pushTaskListByDeviceId(Integer deviceId) { // 改public，让外部能调用
        try {
            // 查询该设备最新的任务列表
            List<DeviceTaskVO> taskList = deviceTaskService.queryTaskRecord(deviceId);
            WebSocketResult taskResult = new WebSocketResult(
                    "task_list", // 消息类型：任务列表
                    JSON.toJSONString(taskList)
            );
            String taskMsg = JSON.toJSONString(taskResult);

            // 遍历该设备的所有连接会话，逐个推送
            for (Map.Entry<Integer, WebSocketSession> entry : device_instance_map.entrySet()) {
                if (entry.getKey().equals(deviceId) && entry.getValue().isOpen()) {
                    entry.getValue().sendMessage(new TextMessage(taskMsg));
                }
            }
            log.debug("【任务列表主动推送】设备ID：{}，任务数：{}", deviceId, taskList.size());
        } catch (Exception e) {
            log.error("【任务列表主动推送失败】设备ID：{}", deviceId, e);
        }
    }
}
