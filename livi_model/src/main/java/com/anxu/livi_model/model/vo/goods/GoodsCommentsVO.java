package com.anxu.livi_model.model.vo.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 【商品评论VO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCommentsVO {
    private Long commentId;//商品评论ID

    private Integer goodsId;//商品id,关联商品表

    private Integer userId;//用户id,关联用户表

    private String userName;//用户名

    private String userAvatar;//用户头像

    private String commentContent;//评论内容

    private Double commentScore;//评论评分

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
}
