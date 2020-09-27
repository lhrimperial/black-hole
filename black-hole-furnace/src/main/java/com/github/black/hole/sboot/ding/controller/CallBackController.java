package com.github.black.hole.sboot.ding.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hairen.long
 * @date 2020/9/27
 */
@RestController
@RequestMapping("/ding")
public class CallBackController {

    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public Object dingCallback(
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") Long timestamp,
            @RequestParam(value = "nonce") String nonce,
            @RequestBody(required = false) JSONObject json) {

        return null;
    }
}
