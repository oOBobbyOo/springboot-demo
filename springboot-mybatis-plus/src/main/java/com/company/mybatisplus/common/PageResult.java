package com.company.mybatisplus.common;

import lombok.Data;

import java.util.List;

/**
 * 统一分页结果类
 *
 * @param <T> 数据类型
 * @author Henry
 * @since 2026-08-10
 */
@Data
public class PageResult<T> {

    /** 当前页码 */
    private Long pageNum;

    /** 每页条数 */
    private Long pageSize;

    /** 总记录数 */
    private Long total;

    /** 总页数 */
    private Long pages;

    /** 当前页数据列表 */
    private List<T> records;

    public PageResult() {}

    public PageResult(Long pageNum, Long pageSize, Long total, Long pages, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.records = records;
    }
}