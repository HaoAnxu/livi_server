package com.anxu.smarthomeunity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.anxu.smarthomeunity.mapper.device.DeviceInfoMapper;
import com.anxu.smarthomeunity.mapper.user.FamilyInfoMapper;
import com.anxu.smarthomeunity.mapper.user.FamilyRelaMapper;
import com.anxu.smarthomeunity.mapper.user.RoomMapper;
import com.anxu.smarthomeunity.mapper.user.UserMapper;
import com.anxu.smarthomeunity.model.dto.device.DeviceInfoDTO;
import com.anxu.smarthomeunity.model.entity.device.DeviceInfoEntity;
import com.anxu.smarthomeunity.model.entity.user.UserInfoEntity;
import com.anxu.smarthomeunity.model.vo.device.DeviceInfoVO;
import com.anxu.smarthomeunity.model.vo.user.UserFamilyRoomVO;
import com.anxu.smarthomeunity.model.vo.user.UserFamilyVO;
import com.anxu.smarthomeunity.service.DeviceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备服务实现类
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 11:09
 */
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FamilyInfoMapper familyInfoMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private FamilyRelaMapper familyRelaMapper;

    /**
     * 查询用户所有设备信息列表
     *
     * @param familyId 家庭ID
     * @return 设备信息实体列表
     */
    @Override
    public List<DeviceInfoVO> queryMyDeviceList(Integer familyId) {
        QueryWrapper<DeviceInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        List<DeviceInfoEntity> deviceInfoEntityList = deviceInfoMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(deviceInfoEntityList, DeviceInfoVO.class);
    }

    /**
     * 查询单个设备信息
     *
     * @param deviceId 设备ID
     * @return 设备信息VO
     */
    @Override
    public DeviceInfoVO queryMyDevice(Integer deviceId) {
        DeviceInfoEntity deviceInfoEntity = deviceInfoMapper.selectById(deviceId);
        DeviceInfoVO deviceInfoVO = new DeviceInfoVO();
        BeanUtil.copyProperties(deviceInfoEntity, deviceInfoVO);
        // 关联查询家庭名称、用户名称、房间名称
        deviceInfoVO.setFamilyName(familyInfoMapper.selectById(familyRelaMapper.selectById(deviceInfoEntity.getFamilyId()).getFamilyId()).getFamilyName());
        deviceInfoVO.setUserName(userMapper.selectById(familyRelaMapper.selectById(deviceInfoEntity.getFamilyId()).getUserId()).getUsername());
        deviceInfoVO.setRoomName(roomMapper.selectById(deviceInfoEntity.getRoomId()).getRoomName());
        return deviceInfoVO;
    }

    /**
     * 更改设备执行状态
     *
     * @param deviceId     设备ID
     * @param deviceStatus 设备状态
     * @return 是否成功
     */
    @Override
    public boolean changeStatus(Integer deviceId, Integer deviceStatus) {
        deviceInfoMapper.updateByDeviceId(deviceId, deviceStatus);
        return true;
    }

    /**
     * 添加新设备
     *
     * @param deviceInfoDTO 设备信息DTO
     * @return 是否成功
     */
    @Override
    public boolean addDevice(DeviceInfoDTO deviceInfoDTO) {
        DeviceInfoEntity deviceInfoEntity = BeanUtil.copyProperties(deviceInfoDTO, DeviceInfoEntity.class);
        return deviceInfoMapper.insert(deviceInfoEntity) > 0;
    }

    /**
     * 删除设备
     *
     * @param deviceId 设备ID
     * @return 是否成功
     */
    @Override
    public boolean deleteDevice(Integer deviceId) {
        return deviceInfoMapper.deleteById(deviceId) > 0;
    }

    /**
     * 查询用户所有家庭信息列表
     *
     * @param userId 用户ID
     * @return 家庭信息VO列表
     */
    @Override

    public List<UserFamilyVO> queryMyFamily(Integer userId) {
        return deviceInfoMapper.queryMyFamily(userId);
    }

    /**
     * 查询用户所有房间信息列表
     *
     * @param familyId 家庭ID
     * @return 房间信息VO列表
     */
    @Override
    public List<UserFamilyRoomVO> queryMyRoomList(Integer familyId) {
        return deviceInfoMapper.queryMyRoomList(familyId);
    }

}
