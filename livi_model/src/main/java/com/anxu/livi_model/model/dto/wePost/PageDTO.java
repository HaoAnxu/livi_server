package com.anxu.livi_model.model.dto.wePost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 【分页DTO】
 *
 * @Author: haoanxu
 * @Date: 2025/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Integer id;
    private Integer page;
    private Integer pageSize;
}
