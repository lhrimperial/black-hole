package com.github.black.hole.gateway.processor.node;

import com.github.black.hole.gateway.entity.Datagram;
import com.github.black.hole.gateway.enums.ResultCode;
import com.github.black.hole.gateway.service.config.PathConfig;
import lombok.Builder;
import lombok.Data;

import java.util.Iterator;
import java.util.List;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
@Builder
public class NodeContext {

    private PathConfig config;

    private Datagram<String> datagram;

    private Iterator<NodeProcessor> processors;

    private Object response;

    public NodeResult invoke() {
        if (processors.hasNext()) {
            NodeProcessor processor = processors.next();
            NodeResult result = processor.execute(this);
            return result;
        }

        return NodeResult.builder()
                .code(ResultCode.SUCCESS.getCode())
                .message(ResultCode.SUCCESS.getMessage())
                .build();
    }
}
