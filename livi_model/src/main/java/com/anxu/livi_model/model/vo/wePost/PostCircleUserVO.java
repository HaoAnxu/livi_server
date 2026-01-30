package com.anxu.livi_model.model.vo.wePost;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【用户-圈子关联VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/23
 */
@Data
public class PostCircleUserVO {
    private Integer id;
    private Integer circleId;
    private LocalDateTime createTime;
}
