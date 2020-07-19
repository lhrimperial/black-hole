package com.github.black.hole.gateway.processor.node;

import com.github.black.hole.gateway.service.config.PathConfig;
import org.springframework.stereotype.Service;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Service
public class DispatchProcessor implements NodeProcessor {
    @Override
    public NodeResult execute(NodeContext context) {

        // TODO 分发内部应用
        String response = "请求成功";
        context.setResponse(response);
        return context.invoke();
    }

    @Override
    public NodeTypeEnum nodeType() {
        return NodeTypeEnum.DISPATCH;
    }

    @Override
    public boolean isWork(PathConfig config) {
        return true;
    }
}
