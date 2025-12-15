package com.anxu.smarthomeunity.controller;

import com.anxu.smarthomeunity.model.Result.Result;
import com.anxu.smarthomeunity.model.dto.device.DeviceInfoDTO;
import com.anxu.smarthomeunity.model.dto.device.DeviceTaskDTO;
import com.anxu.smarthomeunity.service.DeviceService;
import com.anxu.smarthomeunity.service.DeviceTaskService;
import com.anxu.smarthomeunity.util.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 家居设备相关接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 10:40
 */
@Slf4j
@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceTaskService deviceTaskService;
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    //查询用户家庭信息列表
    @GetMapping("/permission/device/queryMyFamilyList")
    public Result queryMyFamily(@RequestParam Integer userId) {
        log.info("<查询用户家庭信息列表>，参数(userId)：{}", userId);
        if (userId == null) {
            return Result.error("用户ID不能为空");
        }
        return Result.success(deviceService.queryMyFamily(userId));
    }

    //查询家庭匹配房间列表
    @GetMapping("/permission/device/queryMyRoomList")
    public Result queryMyRoomList(@RequestParam Integer familyId) {
        log.info("<查询家庭匹配房间列表>，参数(familyId)：{}", familyId);
        if (familyId == null) {
            return Result.error("家庭ID不能为空");
        }
        return Result.success(deviceService.queryMyRoomList(familyId));
    }

    //获取所有设备信息列表
    @GetMapping("/permission/device/queryMyDeviceList")
    public Result queryMyDeviceList(@RequestParam Integer familyId) {
        log.info("<查询所有设备信息列表>，参数(familyId)：{}", familyId);
        if (familyId == null) {
            return Result.error("家庭ID不能为空");
        }
        return Result.success(deviceService.queryMyDeviceList(familyId));
    }

    //获取单个设备信息
    @GetMapping("/permission/device/queryMyDevice")
    public Result queryMyDevice(@RequestParam Integer deviceId) {
        log.info("<查询单个设备信息>，参数(deviceId)：{}", deviceId);
        if (deviceId == null) {
            return Result.error("设备ID不能为空");
        }
        return Result.success(deviceService.queryMyDevice(deviceId));
    }

    //更改设备执行状态(0-关闭，1-执行中,2-异常,3-等待执行)
    @PutMapping("/permission/device/changeStatus")
    public Result changeStatus(@RequestParam Integer deviceId, @RequestParam Integer deviceStatus) {
        log.info("<更改设备执行状态>，参数(deviceId)：{}，参数(deviceStatus)：{}", deviceId, deviceStatus);
        if (deviceId == null) {
            return Result.error("设备ID不能为空");
        }
        if (deviceStatus == null) {
            return Result.error("状态不能为空");
        }
        if (deviceService.changeStatus(deviceId, deviceStatus)) {
            return Result.success();
        }
        return Result.error("更改状态失败");
    }

    //上传设备图片
    @PostMapping("/permission/device/uploadImage")
    public Result uploadImage(@RequestParam("file") MultipartFile imageFile) {
        log.info("<上传设备图片>，文件名：{}", imageFile.getOriginalFilename());
        if (imageFile == null) {
            return Result.error("图片不能为空");
        }
        try {
            String imageUrl = aliyunOSSOperator.upload(imageFile.getBytes(), Objects.requireNonNull(imageFile.getOriginalFilename()));
            return Result.success(imageUrl);
        } catch (Exception e) {
            log.error("<上传设备图片失败>，文件名：{}", imageFile.getOriginalFilename(), e);
            return Result.error("上传图片失败");
        }
    }

    //添加新设备
    @PostMapping("/permission/device/addDevice")
    public Result addDevice(@RequestBody DeviceInfoDTO deviceInfoDTO) {
        log.info("<添加新设备>，参数(deviceInfoDTO)：{}", deviceInfoDTO);
        if (deviceInfoDTO == null) {
            return Result.error("设备信息不能为空");
        }
        if (deviceService.addDevice(deviceInfoDTO)) {
            return Result.success();
        }
        return Result.error("添加设备失败");
    }

    //删除设备
    @DeleteMapping("/permission/device/deleteDevice")
    public Result deleteDevice(@RequestParam Integer deviceId) {
        log.info("<删除设备>，参数(deviceId)：{}", deviceId);
        if (deviceId == null) {
            return Result.error("设备ID不能为空");
        }
        if (deviceService.deleteDevice(deviceId)) {
            return Result.success();
        }
        return Result.error("删除设备失败");
    }

    //创建设备执行任务
    @PostMapping("/permission/device/createTask")
    public Result createTask(@RequestBody DeviceTaskDTO deviceTaskDTO) {
        log.info("<创建设备执行任务>，参数(deviceTaskDTO)：{}", deviceTaskDTO);
        if (deviceTaskDTO == null) {
            return Result.error("任务信息不能为空");
        }
        String msg = deviceTaskService.createTask(deviceTaskDTO);
        if(msg.equals("success")) {
            return Result.success("创建任务成功");
        }
        return Result.error(msg);
    }

    //查询设备执行任务记录
    @GetMapping("/permission/device/queryTaskList")
    public Result queryTaskRecord(@RequestParam Integer deviceId) {
        log.info("<查询设备执行任务记录>，参数(deviceId)：{}", deviceId);
        if (deviceId == null) {
            return Result.error("设备ID不能为空");
        }
        return Result.success(deviceTaskService.queryTaskRecord(deviceId));
    }

    //手动停止所有类型的正在执行的任务
    @PutMapping("/permission/device/stopRunningTask")
    public Result stopRunningTask(@RequestParam Integer deviceId) {
        log.info("<手动停止所有类型的正在执行的任务>，参数(deviceId)：{}", deviceId);
        if (deviceId == null) {
            return Result.error("设备ID不能为空");
        }
        if (deviceTaskService.stopDeviceRunningTask(deviceId)) {
            return Result.success();
        }
        return Result.error("停止任务失败");
    }
}