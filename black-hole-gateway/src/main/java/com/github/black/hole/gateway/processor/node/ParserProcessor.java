package com.github.black.hole.gateway.processor.node;

import com.github.black.hole.gateway.service.config.PathConfig;
import org.springframework.stereotype.Service;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Service
public class ParserProcessor implements NodeProcessor {
    @Override
    public NodeResult execute(NodeContext context) {
        return context.invoke();
    }

    @Override
    public NodeTypeEnum nodeType() {
        return NodeTypeEnum.PARSER;
    }

    @Override
    public boolean isWork(PathConfig config) {
        return true;
    }
}
