package com.github.black.hole.mybatis.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/11/10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
    /** 页码 */
    private Integer pageIndex;
    /** 分页大小 */
    private Integer pageSize;
    /** 总数 */
    private Integer total;
    /** 数据 */
    private Collection<T> data;

    public static <T> Pagination<T> init() {
        return Pagination.<T>builder()
                .pageSize(0)
                .pageIndex(0)
                .data(Collections.emptyList())
                .total(0)
                .build();
    }

    public static <T> Pagination<T> empty(Page page) {
        return Pagination.<T>builder()
                .pageSize(page.getPageSize())
                .pageIndex(page.getPageIndex())
                .data(Collections.emptyList())
                .total(0)
                .build();
    }

    public static <T> Pagination<T> create(Page page, List<T> data, int count) {
        return Pagination.<T>builder()
                .pageSize(page.getPageSize())
                .pageIndex(page.getPageIndex())
                .data(data)
                .total(count)
                .build();
    }

    public static <T> Pagination<T> wrap(int pageIndex, int pageSize, List<T> data, int count) {
        return Pagination.<T>builder()
                .pageSize(pageSize)
                .pageIndex(pageIndex)
                .data(data)
                .total(count)
                .build();
    }

    public static <T, R> Pagination<R> mappingWrap(
            int pageIndex, int pageSize, List<T> data, int count, Function<T, R> mapper) {
        return Pagination.<R>builder()
                .pageSize(pageSize)
                .pageIndex(pageIndex)
                .data(
                        Optional.ofNullable(data)
                                .map(Collection::stream)
                                .map(x -> x.map(mapper))
                                .map(x -> x.collect(Collectors.toList()))
                                .orElse(null))
                .total(count)
                .build();
    }
}
