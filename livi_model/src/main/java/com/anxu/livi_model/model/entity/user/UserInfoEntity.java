package com.anxu.livi_model.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_info")
public class UserInfoEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//用户id

    private String username;//用户名

    private String password;//密码

    private String email;//邮箱

    private Integer gender;//性别(0 表示男，1 表示女)

    private String signature;//个人签名

    private String avatar;//头像

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间

}
