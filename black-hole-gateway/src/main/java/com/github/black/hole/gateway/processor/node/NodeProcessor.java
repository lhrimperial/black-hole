package com.github.black.hole.gateway.processor.node;

import com.github.black.hole.gateway.service.config.PathConfig;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
public interface NodeProcessor {

    /**
     * run
     *
     * @param context
     * @return
     */
    NodeResult execute(NodeContext context);

    /**
     * 节点类型
     *
     * @return
     */
    NodeTypeEnum nodeType();

    /**
     * 是否有效
     *
     * @param config
     * @return
     */
    boolean isWork(PathConfig config);
}
