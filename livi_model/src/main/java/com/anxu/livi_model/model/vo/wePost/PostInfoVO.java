package com.anxu.livi_model.model.vo.wePost;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 【帖子信息VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/19 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoVO {
    private Integer postId; // 帖子唯一ID

    private Integer circleId; // 圈子ID

    private String circleName; // 圈子名称

    private Integer userId; // 用户ID

    private String userName; // 用户名

     private String userAvatar; // 用户头像

    private String postTitle; // 帖子标题

    private String postContent; // 帖子内容

    private String postImage; // 帖子图片

    private Long commentCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间
}
