package com.github.black.hole.sboot.ding.controller;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.github.black.hole.sboot.common.ServiceException;
import com.github.black.hole.sboot.ding.AccessTokenDTO;
import com.github.black.hole.sboot.ding.AccessTokenHandler;
import com.github.black.hole.sboot.ding.DingAppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2020/9/27
 */
@RestController
@RequestMapping("/ding")
public class RegisterController {

    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired DingAppConfig crowdOrgConfig;
    @Autowired AccessTokenHandler accessTokenHandler;

    private String[] callEvents = {
        "user_add_org",
        "user_modify_org",
        "user_leave_org",
        "user_active_org",
        "org_admin_add",
        "org_admin_remove",
        "org_dept_create",
        "org_dept_modify",
        "org_dept_remove",
        "org_remove",
        "org_change",
        "label_user_change",
        "label_conf_add",
        "label_conf_del",
        "label_conf_modify"
    };

    @RequestMapping("/register")
    public void register() throws ServiceException {
        try {
            AccessTokenDTO accessToken =
                    accessTokenHandler.getAccessToken(
                            crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
            DingTalkClient client =
                    new DefaultDingTalkClient(
                            "https://oapi.dingtalk.com/call_back/register_call_back");
            OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
            request.setUrl("http://ifarmshop.com:8080/ding/callback");
            request.setAesKey(crowdOrgConfig.getAesKey());
            request.setToken(crowdOrgConfig.getToken());
            request.setCallBackTag(Arrays.asList(callEvents));
            OapiCallBackRegisterCallBackResponse response =
                    client.execute(request, accessToken.getAccessToken());
            logger.info(JSON.toJSONString(response));
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
