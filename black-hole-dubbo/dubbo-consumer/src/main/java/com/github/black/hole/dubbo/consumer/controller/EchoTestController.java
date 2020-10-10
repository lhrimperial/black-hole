package com.github.black.hole.dubbo.consumer.controller;

import com.github.black.hole.dubbo.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.service.EchoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
@RestController
public class EchoTestController {

    @Reference
    private UserService userService;

    @RequestMapping("/echo/test")
    public String echoTest() {
        // 远程服务引用

        // 强制转型为EchoService
        EchoService echoService = (EchoService) userService;

        // 回声测试可用性
        String status = (String) echoService.$echo("OK");

        assert (status.equals("OK"));

        return "OK";
    }
}
