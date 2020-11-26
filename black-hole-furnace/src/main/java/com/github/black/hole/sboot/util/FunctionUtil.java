package com.github.black.hole.sboot.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hairen.long
 * @date 2020/10/23
 */
@UtilityClass
public class FunctionUtil {
    private final Logger LOG = LoggerFactory.getLogger(FunctionUtil.class);

    /**
     * @param functionWithException 方法体
     * @param param 具体参数值
     * @param <T> 参数
     * @param <R> 返回结果
     * @param <E> 异常
     * @return 无抛出异常的方法
     */
    public <T, R, E extends Exception> R avoidException(
            FunctionWithException<T, R, E> functionWithException, T param) {
        R r = null;
        try {
            r = functionWithException.apply(param);
        } catch (Exception e) {
            LOG.warn("", e);
        }
        return r;
    }

    /**
     * @param functionExceptionWithTwoParam 方法体
     * @param param1 具体参数值
     * @param param2 具体参数值
     * @param <T> 参数
     * @param <R> 返回结果
     * @param <E> 异常
     * @return 无抛出异常的方法
     */
    public static <T, U, R, E extends Exception> R avoidException(
            FunctionExceptionWithTwoParam<T, U, R, E> functionExceptionWithTwoParam,
            T param1,
            U param2) {
        R r = null;
        try {
            r = functionExceptionWithTwoParam.apply(param1, param2);
        } catch (Exception e) {
            LOG.warn("", e);
        }
        return r;
    }

    public interface FunctionExceptionWithTwoParam<T, U, R, E extends Exception> {
        /**
         * Applies this function to the given argument.
         *
         * @param u the function argument
         * @param t the function argument
         * @return the function result
         * @throws E the function exception
         */
        R apply(T t, U u) throws E;
    }

    public interface FunctionWithException<T, R, E extends Exception> {
        /**
         * Applies this function to the given argument.
         *
         * @param t the function argument
         * @return the function result
         * @throws E the function exception
         */
        R apply(T t) throws E;
    }
}
