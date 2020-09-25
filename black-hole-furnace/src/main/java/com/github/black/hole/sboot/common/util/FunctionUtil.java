package com.github.black.hole.sboot.common.util;

import com.github.black.hole.sboot.common.constant.BatchConstant;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hairen.long
 * @date 2020/7/9
 */
@UtilityClass
public class FunctionUtil {
    /** 字符串拆分 */
    private final String STRING_SPLIT = "[\\[,\\]]";

    /**
     * 批量操作
     *
     * @param consumer
     * @param param
     * @param <T>
     */
    public <T> void batchOperation(Consumer<List<T>> consumer, List<T> param) {
        if (CollectionUtils.isEmpty(param)) {
            return;
        }
        List<List<T>> partitions = Lists.partition(param, BatchConstant.DB_BATCH_SIZE);
        partitions.forEach(part -> consumer.accept(part));
    }

    /**
     * 获取列表中某个字段的列表
     *
     * @param items
     * @param getField
     * @param <P>
     * @param <T>
     * @return
     */
    public <P, T> List<T> getFieldList(Collection<P> items, Function<P, T> getField) {
        if (CollectionUtils.isEmpty(items) || Objects.isNull(getField)) {
            return Collections.emptyList();
        }
        return items.stream()
                .filter(Objects::nonNull)
                .map(getField)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 按字段分组
     *
     * @param collection
     * @param fieldFunc
     * @param <P>
     * @param <T>
     * @return
     */
    public <P, T> Map<T, List<P>> groupByField(Collection<P> collection, Function<P, T> fieldFunc) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyMap();
        }
        return collection.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(fieldFunc));
    }

    /**
     * 拆分并且转化String
     *
     * @param str
     * @param convertFunc
     * @param <T>
     * @return
     */
    public <T> List<T> splitString2List(String str, Function<String, T> convertFunc) {
        if (Strings.isNullOrEmpty(str)) {
            return Collections.emptyList();
        }
        return Stream.of(str.split(STRING_SPLIT))
                .filter(item -> !Strings.isNullOrEmpty(item))
                .map(convertFunc)
                .collect(Collectors.toList());
    }

    /**
     * 根据字段过滤
     *
     * @param collection
     * @param predicate
     * @param <P>
     * @return
     */
    public <P> List<P> filterByField(Collection<P> collection, Predicate<P> predicate) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream()
                .filter(Objects::nonNull)
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
