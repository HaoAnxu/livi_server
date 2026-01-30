package com.anxu.livi_model.model.entity.user;

/**
 *
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 13:39
 */
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 家庭组关系Entity
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 13:39
 */
@Data
@TableName("user_family_relation")
public class userFamilyRelationEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Integer familyId;//家庭组id

    private Integer userId;//用户id

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间
}
