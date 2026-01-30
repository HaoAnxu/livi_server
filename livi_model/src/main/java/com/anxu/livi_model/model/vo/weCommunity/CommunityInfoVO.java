package com.anxu.livi_model.model.vo.weCommunity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 圈子信息DTO
 *
 * @Author: haoanxu
 * @Date: 2025/11/26 13:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityInfoVO {
    private Integer communityId;
    private String communityName;
    private String communityImage;
    private String communityDesc;
    private Integer communityMembers;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
