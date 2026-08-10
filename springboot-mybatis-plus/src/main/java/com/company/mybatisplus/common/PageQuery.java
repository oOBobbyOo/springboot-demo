package com.company.mybatisplus.common;

import lombok.Data;

/**
 * 分页查询请求基类
 *
 * @author Henry
 * @since 2026-08-10
 */
@Data
public class PageQuery {

    /** 当前页码，默认第1页 */
    private Integer pageNum = 1;

    /** 每页条数，默认10条 */
    private Integer pageSize = 10;
}