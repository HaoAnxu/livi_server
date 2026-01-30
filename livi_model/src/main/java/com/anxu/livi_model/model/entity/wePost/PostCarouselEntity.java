package com.anxu.livi_model.model.entity.wePost;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 【轮播图Entity】
 *
 * @Author: haoanxu
 * @Date: 2025/12/22
 */
@Data
@TableName("pub_post_carousel")
public class PostCarouselEntity {
    @TableId(value = "carousel_id", type = IdType.AUTO)
    private Integer carouselId;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
