package com.anxu.livi_model.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户家庭信息VO
 *
 * @Author: haoanxu
 * @Date: 2025/12/10 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFamilyVO {
    private Integer familyId;
    private String familyName;
}
