package com.github.black.hole.template.application.controller;

import com.github.black.hole.template.api.user.dto.UserDTO;
import com.github.black.hole.template.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
@RestController
public class UserController {

    @Autowired UserService userService;

    @RequestMapping("/user/find/{userId}")
    public UserDTO findUser(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }
}
