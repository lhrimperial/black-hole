package com.github.black.hole.sboot.ding.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.github.black.hole.sboot.ding.DingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hairen.long
 * @date 2020/10/23
 */
public class AbstractCrowd {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected String getToken() throws Exception {
        DefaultDingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dinguenpya4moj9yzuju");
        request.setAppsecret("oPlzb-72XCVbpLUUqB3sRNR2KQwxtqnbqi2bki_dGtN9Zi5jPWfy6qqtOyKZM2de");
        request.setHttpMethod(DingConstants.GET);
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }
}
