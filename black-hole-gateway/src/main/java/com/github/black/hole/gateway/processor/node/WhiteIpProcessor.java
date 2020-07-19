package com.github.black.hole.gateway.processor.node;

import com.github.black.hole.gateway.service.config.PathConfig;
import org.springframework.stereotype.Service;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Service
public class WhiteIpProcessor implements NodeProcessor {

    @Override
    public NodeResult execute(NodeContext context) {

        if (includeWhiteIP()) {
            return context.invoke();
        }
        // TODO
        return NodeResult.builder().message("不在白名单中").build();
    }

    private boolean includeWhiteIP() {
        return true;
    }

    @Override
    public NodeTypeEnum nodeType() {
        return NodeTypeEnum.WHITE_IP;
    }

    @Override
    public boolean isWork(PathConfig config) {
        // TODO 可根据config
        return true;
    }
}
