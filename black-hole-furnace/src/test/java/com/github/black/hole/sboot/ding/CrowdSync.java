package com.github.black.hole.sboot.ding;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.github.black.hole.sboot.SbootMain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static String getToken() throws Exception {
        String appKey = "dinguenpya4moj9yzuju";
        String appSecret = "oPlzb-72XCVbpLUUqB3sRNR2KQwxtqnbqi2bki_dGtN9Zi5jPWfy6qqtOyKZM2de";
        // 测试
        //        String appKey = "ding2qurqklmvjg5pxlm";
        //        String appSecret =
        // "_PWdtl6GrmClZFu-Vn9nP7a9P1ri269BmEGHbMbxbKqyAM4rFXBczU1voinktphr";
        DefaultDingTalkClient client = new DefaultDingTalkClient(DingUrlConstants.GET_TOKEN);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod(DingConstants.GET);
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }

    public static void sendMessage() throws Exception {
        DingTalkClient client =
                new DefaultDingTalkClient(
                        "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request =
                new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(789887943L);
        request.setUseridList("0948453840152451");
        request.setToAllUser(true);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg =
                new OapiMessageCorpconversationAsyncsendV2Request.Msg();

        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText("head");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setContent("xxx");
        msg.setMsgtype("oa");
        request.setMsg(msg);

        System.out.println(JSON.toJSONString(msg));

        OapiMessageCorpconversationAsyncsendV2Response rsp = client.execute(request, getToken());
        System.out.println(rsp.getBody());
    }

    public static void main(String[] args) throws Exception {
        getUserByDept();
    }

    public static void getUserByDept() throws Exception {
        long offset = 0L;
        boolean isFinish = false;
        List<OapiUserListbypageResponse.Userlist> userIds = Lists.newArrayList();
        Long[] depts = {395015587L, 395242143L, 374341616L, 413028436L, 412937750L, 81721025L};
        for (Long deptId : depts) {
            do {
                DingTalkClient client2 =
                        new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
                OapiUserListbypageRequest request2 = new OapiUserListbypageRequest();
                request2.setDepartmentId(deptId);
                request2.setOffset(offset);
                request2.setSize(100L);
                OapiUserListbypageResponse response2 = client2.execute(request2, getToken());
                userIds.addAll(response2.getUserlist());
                offset += response2.getUserlist().size();
                isFinish =
                        CollectionUtils.isEmpty(response2.getUserlist())
                                || response2.getUserlist().size() == 0;
            } while (!isFinish);
        }
        List<OapiUserListbypageResponse.Userlist> result =
                userIds.stream()
                        .filter(item -> Objects.isNull(item.getActive()))
                        .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void test111() throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid("2538560432914701");
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, getToken());
        String ext = response.getExtattr();
        @SuppressWarnings("unchecked")
        Map<String, String> extMap = JSON.parseObject(ext, Map.class);
    }

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
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        logger.info("allDepartment:{}", JSON.toJSONString(response.getDepartment()));
    }
}
