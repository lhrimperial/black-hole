package com.github.black.hole.mybatis.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author hairen.long
 * @date 2020/11/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPage {

    /** 页码 */
    private Integer pageIndex;

    /** 分页大小 */
    private Integer pageSize;

    /** 总数 */
    private Integer total;

    /** 参数 */
    private Object param;

    public static QueryPage toQueryPage(Pagination<?> pagination, Object params) {
        return QueryPage.builder()
                .pageSize(pagination.getPageSize())
                .pageIndex(pagination.getPageIndex())
                .param(params)
                .build();
    }

    public <T> Pagination<T> toPagination(Collection<T> data) {
        return Pagination.<T>builder()
                .total(this.getTotal())
                .pageIndex(this.getPageIndex())
                .pageSize(this.getPageSize())
                .data(data)
                .build();
    }
}
