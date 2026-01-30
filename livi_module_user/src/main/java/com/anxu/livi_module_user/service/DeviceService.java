package com.anxu.livi_module_user.service;

import com.anxu.livi_model.model.dto.device.DeviceInfoDTO;
import com.anxu.livi_model.model.vo.device.DeviceInfoVO;
import com.anxu.livi_model.model.vo.user.UserFamilyRoomVO;
import com.anxu.livi_model.model.vo.user.UserFamilyVO;

import java.util.List;

/**
 * 设备服务接口
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 11:09
 */
public interface DeviceService {
    //查询所有设备信息
    List<DeviceInfoVO> queryMyDeviceList(Integer familyId);
    //查询单个设备信息
    DeviceInfoVO queryMyDevice(Integer deviceId);
    //更改设备执行状态
    boolean changeStatus(Integer deviceId, Integer deviceStatus);
    //添加新设备
    boolean addDevice(DeviceInfoDTO deviceInfoDTO);
    //删除设备
    boolean deleteDevice(Integer deviceId);
    //查询用户所有家庭信息
    List<UserFamilyVO> queryMyFamily(Integer userId);
    //查询家庭匹配房间列表
    List<UserFamilyRoomVO> queryMyRoomList(Integer familyId);
}
