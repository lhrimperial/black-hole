package com.github.black.hole.template.application.service;

import com.github.black.hole.template.api.user.dto.UserDTO;
import com.github.black.hole.template.api.user.service.UserService;
import com.github.black.hole.template.orm.database.user.dao.UserMapper;
import com.github.black.hole.template.orm.database.user.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired UserMapper userMapper;

    @Override
    public UserDTO findUserById(Long userId) {
        UserDO userDO = userMapper.findById(userId);
        if (Objects.isNull(userDO)) {
            return null;
        }
        return UserDTO.builder().userId(userDO.getId()).userName(userDO.getName()).build();
    }
}
