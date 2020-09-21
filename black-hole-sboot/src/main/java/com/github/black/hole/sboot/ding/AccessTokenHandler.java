package com.github.black.hole.sboot.ding;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.github.black.hole.sboot.ding.DingConstants.MILLISECOND_STEP;

/**
 * @author hairen.long
 * @date 2020/5/8
 */
@Component
public class AccessTokenHandler {

    private Logger logger = LoggerFactory.getLogger(AccessTokenHandler.class);

    public AccessTokenDTO getAccessToken(String appKey, String appSecret) {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingUrlConstants.GET_TOKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(appKey);
            request.setAppsecret(appSecret);
            request.setHttpMethod(DingConstants.GET);
            OapiGettokenResponse response = client.execute(request);
            if (Objects.nonNull(response)
                    && Objects.equals(
                            DingErrorCodeEnum.SUCCESS.getErrCode(), response.getErrcode())) {
                return AccessTokenDTO.builder()
                        .accessToken(response.getAccessToken())
                        .expireTime(
                                System.currentTimeMillis()
                                        + (response.getExpiresIn() * MILLISECOND_STEP))
                        .build();
            }
            logger.warn(
                    "获取AccessToken失败，appKey={},appSecret={},response={}",
                    appKey,
                    appSecret,
                    JSON.toJSONString(response));
        } catch (ApiException e) {
            logger.error("获取AccessToken异常，appKey={},appSecret={}", appKey, appSecret, e);
        }
        return null;
    }
}
