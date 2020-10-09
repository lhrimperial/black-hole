package com.github.black.hole.dubbo.consumer.reference;

import com.github.black.hole.dubbo.api.dto.UserDTO;
import com.github.black.hole.dubbo.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/10/9
 */
@Component
public class UserQueryHandler {

    @Reference private UserService userService;

    public List<UserDTO> getAllUser() {
        return userService.listUser();
    }
}
