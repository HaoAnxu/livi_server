package com.anxu.livi_model.model.entity.wePost;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【帖子Entity】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 14:59
 */
@Data
@TableName("pub_post_info")
public class PostInfoEntity {
    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId; // 帖子唯一ID

    private Integer circleId; // 圈子ID

    private Integer userId; // 用户ID

    private String postTitle; // 帖子标题

    private String postContent; // 帖子内容

    private String postImage; // 帖子图片

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间
}
