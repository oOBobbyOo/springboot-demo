package com.company.mybatisplus.dto;

import com.company.mybatisplus.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色分页条件查询参数
 *
 * @author Henry
 * @since 2026-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQuery extends PageQuery {

    /** 角色名称，模糊匹配 */
    private String roleName;

    /** 角色编码，模糊匹配 */
    private String roleCode;

    /** 状态：0禁用，1启用 */
    private Byte status;
}