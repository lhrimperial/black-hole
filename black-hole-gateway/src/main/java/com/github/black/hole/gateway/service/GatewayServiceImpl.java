package com.github.black.hole.gateway.service;

import com.github.black.hole.gateway.entity.ExecuteRequest;
import com.github.black.hole.gateway.entity.ExecuteResult;
import com.github.black.hole.gateway.processor.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Service
public class GatewayServiceImpl implements GatewayService {

    @Autowired private ExecuteService executeService;

    @Override
    public ExecuteResult execute(GatewayContext context) {
        // TODO 做线程池隔离

        ExecuteRequest request =
                ExecuteRequest.builder()
                        .config(context.getConfig())
                        .datagram(context.getRequestData())
                        .build();

        ExecuteResult result = executeService.execute(request);

        // TODO 重试

        return result;
    }
}
