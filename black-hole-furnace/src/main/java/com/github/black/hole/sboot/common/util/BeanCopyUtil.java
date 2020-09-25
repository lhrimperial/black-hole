package com.github.black.hole.sboot.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 拷贝对象类，因为很多时候会拷贝一个list的对象，需要写for循环，所以在这里写了个util类
 *
 * @author dlsscly
 */
@UtilityClass
public class BeanCopyUtil {

    /**
     * 将一个list的对象拷贝转化为另外一个类型的list
     *
     * @param ks 原list
     * @param vClass 需要转化为的对象类型
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <K, V> List<V> transform(List<K> ks, Class<V> vClass) throws RuntimeException {
        return transform(ks, vClass, (String[]) null);
    }

    /**
     * 将一个list的对象拷贝转化为另外一个类型的list
     *
     * @param ks 原list
     * @param vClass 需要转化为的对象类型
     * @param ignoreFields 忽略的属性
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <K, V> List<V> transform(List<K> ks, Class<V> vClass, String... ignoreFields)
            throws RuntimeException {
        if (ks == null || ks.size() == 0) {
            return Collections.emptyList();
        }
        List<V> vs = new ArrayList<>();
        try {
            for (K k : ks) {
                if (null == k) {
                    continue;
                }
                V v2 = vClass.getDeclaredConstructor().newInstance();
                if (null == ignoreFields) {

                    BeanUtils.copyProperties(k, v2);
                } else {

                    BeanUtils.copyProperties(k, v2, ignoreFields);
                }
                vs.add(v2);
            }
        } catch (Exception e) {
            throw new RuntimeException("bean copy fail.");
        }
        return vs;
    }

    /**
     * @param ks
     * @param vClass
     * @param <K>
     * @param <V>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <K, V> V transform(K ks, Class<V> vClass) throws RuntimeException {
        try {
            if (ks == null) {
                return null;
            }
            V vs = vClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(ks, vs);
            return vs;
        } catch (Exception e) {
            throw new RuntimeException("bean copy fail.");
        }
    }

    /**
     * @param ks
     * @param vClass
     * @param ignoreFields
     * @param <K>
     * @param <V>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <K, V> V transform(K ks, Class<V> vClass, String... ignoreFields)
            throws RuntimeException {
        if (null == ks) {
            return null;
        }
        try {
            V vs = vClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(ks, vs, ignoreFields);
            return vs;
        } catch (Exception e) {
            throw new RuntimeException("bean copy fail.");
        }
    }
}
