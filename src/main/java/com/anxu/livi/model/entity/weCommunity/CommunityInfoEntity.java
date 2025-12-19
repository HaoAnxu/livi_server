package com.anxu.livi.model.entity.weCommunity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 圈子信息实体类Entity
 *
 * @Author: haoanxu
 * @Date: 2025/11/26 13:34
 */
@Data
@TableName("pub_community")
public class CommunityInfoEntity {
    @TableId(value = "community_id",type = IdType.AUTO)
    private Integer communityId;
    private String communityName;
    private String communityImage;
    private String communityDesc;
    private Integer communityMembers;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
