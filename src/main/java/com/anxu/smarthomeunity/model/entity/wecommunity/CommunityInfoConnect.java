package com.anxu.smarthomeunity.model.entity.wecommunity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 信息关联表Entity
 *
 * @Author: haoanxu
 * @Date: 2025/11/21 13:50
 */
@Data
@TableName("pub_user_community_info_connect")
public class CommunityInfoConnect {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Long msgId;

    private Integer circleId;

    private Integer readStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
