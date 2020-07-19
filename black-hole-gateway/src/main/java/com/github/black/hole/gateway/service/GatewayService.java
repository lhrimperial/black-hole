package com.github.black.hole.gateway.service;

import com.github.black.hole.gateway.entity.ExecuteResult;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
public interface GatewayService {

    /**
     * execute
     *
     * @param context
     * @return
     */
    ExecuteResult execute(GatewayContext context);
}
