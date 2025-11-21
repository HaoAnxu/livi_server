package com.anxu.smarthomeunity.model.entity.wecommunity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 圈子和用户关联表Entity
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 14:35
 */
@Data
@TableName("pub_community_and_user")
public class CommunityAndUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer communityId;

    private Integer userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
