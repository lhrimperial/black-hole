package com.github.black.hole.gateway.processor;

import com.github.black.hole.gateway.entity.Datagram;
import com.github.black.hole.gateway.entity.ExecuteRequest;
import com.github.black.hole.gateway.entity.ExecuteResult;
import com.github.black.hole.gateway.processor.node.NodeContext;
import com.github.black.hole.gateway.processor.node.NodeProcessor;
import com.github.black.hole.gateway.processor.node.NodeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Service
public class ExecuteServiceImpl implements ExecuteService {

    @Autowired private List<NodeProcessor> processors;

    @Override
    public ExecuteResult execute(ExecuteRequest request) {
        List<NodeProcessor> sortProcessors =
                processors.stream()
                        .sorted(Comparator.comparing(NodeProcessor::nodeType))
                        .collect(Collectors.toList());

        NodeContext context =
                NodeContext.builder()
                        .config(request.getConfig())
                        .datagram(request.getDatagram())
                        .processors(sortProcessors.iterator())
                        .build();

        NodeResult nodeResult = context.invoke();

        Datagram<String> response = new Datagram<>();
        response.setBody(context.getResponse().toString());
        // TODO 加密 封装报文

        return ExecuteResult.builder()
                .datagram(response)
                .code(nodeResult.getCode())
                .message(nodeResult.getMessage())
                .build();
    }
}
