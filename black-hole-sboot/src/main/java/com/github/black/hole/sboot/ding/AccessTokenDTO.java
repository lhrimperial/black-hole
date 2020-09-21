package com.github.black.hole.sboot.ding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/5/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDTO {
    /** 授权码 */
    private String accessToken;
    /** 过期时间 */
    private Long expireTime;
}
