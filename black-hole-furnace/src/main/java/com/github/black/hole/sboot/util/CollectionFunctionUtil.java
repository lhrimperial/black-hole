package com.github.black.hole.sboot.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/11/10
 */
public class CollectionFunctionUtil {
    /** 批量 */
    private static final int BATCH_SIZE = 600;

    /**
     * 集合中取值
     *
     * @param collection
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> flatList(
            Collection<T> collection, Function<T, Collection<R>> function) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .map(function)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 集合中取值
     *
     * @param collection
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> getList(Collection<T> collection, Function<T, R> function) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .map(function)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 集合中取值
     *
     * @param collection
     * @param filter
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> getList(
            Collection<T> collection, Predicate<T> filter, Function<T, R> function) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .filter(filter)
                .map(function)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 集合中取值
     *
     * @param collection
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Set<R> getSet(Collection<T> collection, Function<T, R> function) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .map(function)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * 集合中取值
     *
     * @param collection
     * @param filter
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Set<R> getSet(
            Collection<T> collection, Predicate<T> filter, Function<T, R> function) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .filter(filter)
                .map(function)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * 集合中取map
     *
     * @param collection
     * @param filter
     * @param keyFun
     * @param valueFun
     * @param <T>
     * @param <K>
     * @param <V>
     * @return
     */
    public static <T, K, V> Map<K, V> getMap(
            Collection<T> collection,
            Predicate<T> filter,
            Function<T, K> keyFun,
            Function<T, V> valueFun) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .filter(filter)
                .filter(item -> Objects.nonNull(keyFun.apply(item)))
                .filter(item -> Objects.nonNull(valueFun.apply(item)))
                .collect(Collectors.toMap(keyFun, valueFun, (v1, v2) -> v1));
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
    public static <T, K, V> Map<K, List<V>> grouping(
            Collection<T> collection, Function<T, K> keyFun, Function<T, V> valueFun) {
        return Optional.ofNullable(collection).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .filter(item -> Objects.nonNull(keyFun.apply(item)))
                .filter(item -> Objects.nonNull(valueFun.apply(item)))
                .collect(
                        Collectors.groupingBy(
                                keyFun, Collectors.mapping(valueFun, Collectors.toList())));
    }

    /**
     * @param sources
     * @param groupFun
     * @param keyFun
     * @param valueFun
     * @param <T>
     * @param <G>
     * @param <K>
     * @param <V>
     * @return
     */
    public static <T, G, K, V> Map<G, Map<K, V>> grouping2Map(
            List<T> sources,
            Function<T, G> groupFun,
            Function<T, K> keyFun,
            Function<T, V> valueFun) {
        return Optional.ofNullable(sources).orElse(Collections.emptyList()).stream()
                .filter(Objects::nonNull)
                .filter(item -> Objects.nonNull(groupFun.apply(item)))
                .filter(item -> Objects.nonNull(keyFun.apply(item)))
                .filter(item -> Objects.nonNull(valueFun.apply(item)))
                .collect(
                        Collectors.groupingBy(
                                groupFun, Collectors.toMap(keyFun, valueFun, (v1, v2) -> v1)));
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
        partition.forEach(consumer);
    }

    /**
     * 批量操作
     *
     * @param params
     * @param consumer
     * @param <T>
     */
    public static <T> void batchOperation(
            Collection<T> params, Consumer<List<T>> consumer, int batch) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        int size = batch == 0 ? BATCH_SIZE : batch;
        List<List<T>> partition = Lists.partition(Lists.newArrayList(params), size);
        partition.forEach(consumer);
    }

    /**
     * 取值
     *
     * @param value
     * @param defaultV
     * @param <T>
     * @return
     */
    public static <T> T getValue(T value, T defaultV) {
        return Objects.isNull(value) ? defaultV : value;
    }

    /**
     * 根据字段去重
     *
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * map 反转聚合
     *
     * @param manyToOneMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<V, Set<K>> reverseMany(Map<K, V> manyToOneMap) {
        if (CollectionUtils.isEmpty(manyToOneMap)) {
            return Collections.emptyMap();
        }

        return manyToOneMap.entrySet().stream()
                .filter(entry -> Objects.nonNull(entry.getValue()))
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getValue,
                                Collectors.mapping(Map.Entry::getKey, Collectors.toSet())));
    }

    /**
     * map 反转拍平
     *
     * @param oneToManyMap
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<V, K> reverseOne(Map<K, ? extends Collection<V>> oneToManyMap) {
        if (CollectionUtils.isEmpty(oneToManyMap)) {
            return Collections.emptyMap();
        }

        return oneToManyMap.entrySet().stream()
                .map(
                        (Map.Entry<K, ? extends Collection<V>> entry) ->
                                entry.getValue().stream()
                                        .filter(Objects::nonNull)
                                        .map(value -> Maps.immutableEntry(value, entry.getKey()))
                                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1));
    }
}
