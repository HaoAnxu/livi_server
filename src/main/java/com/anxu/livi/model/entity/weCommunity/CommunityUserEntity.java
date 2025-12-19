package com.anxu.livi.model.entity.weCommunity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户-社区关联Entity
 *
 * @Author: haoanxu
 * @Date: 2025/11/26 14:24
 */
@Data
@TableName("pub_community_user")
public class CommunityUserEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer communityId;
    private Integer userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
