package com.anxu.livi_model.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户家庭匹配房间VO
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFamilyRoomVO {
    private Integer roomId;//房间id
    private String roomName;//房间名称
}
