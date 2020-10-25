package com.github.black.hole.sboot.ding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author hairen.long
 * @date 2020/9/27
 */
@RestController
@RequestMapping("/ding")
public class CallBackController {

    @Autowired private CrowdSync crowdSync;
    @Autowired private CrowGroup crowGroup;

    @RequestMapping("/call")
    public String call() throws Exception {
        crowdSync.registerCallBack();
        return "OK";
    }

    @RequestMapping("/sendFile")
    public String sendFile() throws Exception {
        CompletableFuture.runAsync(
                () -> {
                    try {
                        crowGroup.statisticsOwner();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return "OK";
    }
}
