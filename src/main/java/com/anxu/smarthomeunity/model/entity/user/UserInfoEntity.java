package com.anxu.smarthomeunity.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfoEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;//用户id

    private String username;//用户名

    private String password;//密码

    private String email;//邮箱

    private Integer gender;//性别(0 表示男，1 表示女)

    private String signature;//个人签名

    private LocalDateTime createTime;//创建时间

    private LocalDateTime updateTime;//更新时间

}
