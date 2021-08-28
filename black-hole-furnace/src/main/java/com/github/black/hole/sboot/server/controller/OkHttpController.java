package com.github.black.hole.sboot.server.controller;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.sboot.server.dto.ManagerDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hairen.long
 * @date 2021/8/11
 */
@RestController
@RequestMapping("/manage")
public class OkHttpController {

    @RequestMapping("/get")
    ManagerDTO getManage(@RequestParam Long bindUserId, @RequestParam String bindName) {
        return ManagerDTO.builder()
                .bindUserId(bindUserId)
                .bindUserName(bindName)
                .resourceName("shanghai")
                .resourceId(1L)
                .build();
    }

    @RequestMapping("/save")
    void saveBind(@RequestBody ManagerDTO manager) {
        System.out.println(JSON.toJSONString(manager));
    }
}
