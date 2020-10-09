package com.github.black.hole.dubbo.api.service;

import com.github.black.hole.dubbo.api.dto.UserDTO;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/10/9
 */
public interface UserService {

    /**
     * list
     *
     * @return
     */
    List<UserDTO> listUser();
}
