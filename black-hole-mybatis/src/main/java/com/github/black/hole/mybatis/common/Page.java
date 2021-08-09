package com.github.black.hole.mybatis.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/11/10
 */
@Data
@AllArgsConstructor
public class Page {
    /** 默认前端页码 */
    public static final Integer DEFAULT_PAGE_INDEX = 1;
    /** 默认前端分页大小 */
    public static final Integer DEFAULT_PAGE_SIZE = 20;
    /** 前端页码 */
    private int pageIndex;
    /** 前端分页大小 */
    private int pageSize;
    /** 后端分页偏移量 */
    private int limit;
    /** 后端分页 */
    private int offset;

    public Page() {
        valueOf(null, null);
    }

    public void valueOf(Integer pageIndex, Integer pageSize) {
        if (checkPageParam(pageIndex) || checkPageParam(pageSize)) {
            pageIndex = DEFAULT_PAGE_INDEX;
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.offset = (pageIndex - 1) * pageSize;
        this.limit = pageSize;
    }

    private boolean checkPageParam(Integer pageParam) {
        return Objects.isNull(pageParam) || pageParam <= 0;
    }
}
