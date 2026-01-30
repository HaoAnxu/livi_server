package com.anxu.livi_model.model.entity.user;

/**
 *
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 13:40
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 家庭组房间Entity
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 13:40
 */
@Data
@TableName("user_family_room")
public class userFamilyRoomEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//主键id

    private Integer familyId;//家庭id

    private Integer roomId;//房间id

    private String roomName;//房间名称

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
