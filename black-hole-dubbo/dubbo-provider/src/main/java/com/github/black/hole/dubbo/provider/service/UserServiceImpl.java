package com.github.black.hole.dubbo.provider.service;

import com.github.black.hole.dubbo.api.dto.UserDTO;
import com.github.black.hole.dubbo.api.service.UserService;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/10/9
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDTO> listUser() {
        return Lists.newArrayList(
                UserDTO.builder().userName("andy").password("123").build(),
                UserDTO.builder().userName("json").password("456").build());
    }

    @Override
    public UserDTO saveAndReturn(UserDTO userDTO) {
        return userDTO;
    }
}
