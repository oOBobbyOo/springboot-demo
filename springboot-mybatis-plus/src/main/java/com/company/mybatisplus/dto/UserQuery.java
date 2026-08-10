package com.company.mybatisplus.dto;

import com.company.mybatisplus.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页条件查询参数
 *
 * @author Henry
 * @since 2026-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PageQuery {

    /** 用户名，模糊匹配 */
    private String userName;

    /** 昵称，模糊匹配 */
    private String nickName;

    /** 角色ID，精确匹配 */
    private Long roleId;

    /** 状态：0禁用，1启用 */
    private Byte status;
}