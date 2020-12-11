package com.github.black.hole.template.application.controller;

import com.github.black.hole.template.api.user.dto.BusinessDTO;
import com.github.black.hole.template.api.user.service.TemplateBusinessService;
import com.github.black.hole.template.integration.app1.InternalPersonDTO;
import com.github.black.hole.template.integration.app1.RpcPersonServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
@RestController
public class UserController {

    @Autowired
    TemplateBusinessService userService;
    @Autowired RpcPersonServiceHandler rpcPersonServiceHandler;

    @RequestMapping("/user/find/{userId}")
    public BusinessDTO findUser(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @RequestMapping("/person/list")
    public List<InternalPersonDTO> findPerson() {
        return rpcPersonServiceHandler.findPersonList();
    }
}
