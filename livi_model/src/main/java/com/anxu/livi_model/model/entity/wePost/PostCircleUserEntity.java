package com.anxu.livi_model.model.entity.wePost;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【用户-圈子关联Entity】
 *
 * @Author: haoanxu
 * @Date: 2025/12/23
 */
@Data
@TableName("pub_post_circle_user")
public class PostCircleUserEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer circleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
