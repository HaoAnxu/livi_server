package com.anxu.livi_model.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 家庭组Entity
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 13:38
 */
@Data
@TableName("user_family")
public class userFamilyEntity {
    @TableId(value = "family_id",type = IdType.AUTO)
    private Integer familyId;//家庭组id

    private String familyName;//家庭组名称

     private LocalDateTime createTime;//创建时间

     private LocalDateTime updateTime;//更新时间
}
