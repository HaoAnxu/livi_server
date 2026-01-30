package com.anxu.livi_module_user.mapper.device;

import com.anxu.livi_model.model.entity.device.DeviceInfoEntity;
import com.anxu.livi_model.model.vo.user.UserFamilyRoomVO;
import com.anxu.livi_model.model.vo.user.UserFamilyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备信息Mapper
 *
 * @Author: haoanxu
 * @Date: 2025/12/4 11:10
 */
@Mapper
public interface DeviceInfoMapper extends BaseMapper<DeviceInfoEntity> {
    //根据设备id更新设备状态
    void updateByDeviceId(Integer deviceId,Integer deviceStatus);
    //根据用户id查询用户所有家庭信息
    List<UserFamilyVO> queryMyFamily(Integer userId);
    //根据家庭id查询家庭所有房间信息
    List<UserFamilyRoomVO> queryMyRoomList(Integer familyId);
}
