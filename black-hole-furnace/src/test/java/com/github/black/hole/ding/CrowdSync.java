package com.github.black.hole.ding;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.github.black.hole.sboot.SbootMain;
import com.github.black.hole.sboot.ding.AccessTokenDTO;
import com.github.black.hole.sboot.ding.AccessTokenHandler;
import com.github.black.hole.sboot.ding.DingAppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hairen.long
 * @date 2020/11/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SbootMain.class)
public class CrowdSync {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired DingAppConfig crowdOrgConfig;
    @Autowired AccessTokenHandler accessTokenHandler;

    @Test
    public void update() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
        OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
        request.setId(418690803L);
        request.setName("临时部门15");
        request.setSourceIdentifier("temporaryGroup_15");
        OapiDepartmentUpdateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getDingDepartment() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId("358518191");
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        logger.info("allDepartment:{}", JSON.toJSONString(response.getDepartment()));
        }
}
