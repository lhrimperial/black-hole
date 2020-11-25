package com.github.black.hole.sboot.util;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/11/10
 */
public class CollectionFunctionUtil {
    /** 批量 */
    private static final int BATCH_SIZE = 200;

    /**
     * 集合中取值
     *
     * @param collection
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> getValues(Collection<T> collection, Function<T, R> function) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .map(function)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 集合中取map
     *
     * @param collection
     * @param keyFun
     * @param valueFun
     * @param <T>
     * @param <K>
     * @param <V>
     * @return
     */
    public static <T, K, V> Map<K, V> getMap(
            Collection<T> collection, Function<T, K> keyFun, Function<T, V> valueFun) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .filter(item -> Objects.nonNull(keyFun.apply(item)))
                .filter(item -> Objects.nonNull(valueFun.apply(item)))
                .collect(Collectors.toMap(keyFun, valueFun, (v1, v2) -> v1));
    }

    /**
     * 批量操作
     *
     * @param params
     * @param consumer
     * @param <T>
     */
    public static <T> void batchOperation(Collection<T> params, Consumer<List<T>> consumer) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        List<List<T>> partition = Lists.partition(Lists.newArrayList(params), BATCH_SIZE);
        partition.forEach(item -> consumer.accept(item));
    }
}
