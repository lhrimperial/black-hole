package com.github.black.hole.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hairen.long
 * @date 2020/9/24
 */
@Data
public class UserEntity implements Serializable {

    /** */
    private static final long serialVersionUID = 5237730257103305078L;

    private Long id;
    private String userName;
    private String userSex;
}
