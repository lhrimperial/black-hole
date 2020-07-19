package com.github.black.hole.gateway.processor;

import com.github.black.hole.gateway.entity.ExecuteRequest;
import com.github.black.hole.gateway.entity.ExecuteResult;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
public interface ExecuteService {

    /**
     * 执行processor
     *
     * @param request
     * @return
     */
    ExecuteResult execute(ExecuteRequest request);
}
