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
public class DingAppConfig {
    /** app id */
    private String agentId;
    /** app key */
    private String appKey;
    /** app 密匙 */
    private String appSecret;

    private String token;

    private String aesKey;
}
