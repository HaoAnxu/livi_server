package com.anxu.smarthomeunity.pojo.pub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {
    private Integer communityId;//社区id
    private String communityName;//社区名称
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
