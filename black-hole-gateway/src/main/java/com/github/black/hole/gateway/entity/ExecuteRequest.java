package com.github.black.hole.gateway.entity;

import com.github.black.hole.gateway.service.config.PathConfig;
import lombok.Builder;
import lombok.Data;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
@Builder
public class ExecuteRequest {

    private PathConfig config;

    private Datagram<String> datagram;
}
