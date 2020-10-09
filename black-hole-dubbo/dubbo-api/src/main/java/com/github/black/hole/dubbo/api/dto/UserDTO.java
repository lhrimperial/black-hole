package com.github.black.hole.dubbo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hairen.long
 * @date 2020/10/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 6982777706430843338L;
    /** name */
    private String userName;
    /** pwd */
    private String password;
}
