package com.github.black.hole.dubbo.consumer.controller;

import com.github.black.hole.dubbo.api.dto.UserDTO;
import com.github.black.hole.dubbo.consumer.reference.UserQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/10/9
 */
@RestController
public class UserController {

    @Autowired private UserQueryHandler userQueryHandler;

    @RequestMapping("/user/list")
    public List<UserDTO> listUser() {
        return userQueryHandler.getAllUser();
    }
}
