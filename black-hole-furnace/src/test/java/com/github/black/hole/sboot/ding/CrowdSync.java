package com.github.black.hole.sboot.ding;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.request.OapiUserUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.github.black.hole.sboot.SbootMain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.codec.binary.Base64;
import org.assertj.core.util.Strings;
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
        //        String appKey = "dinguenpya4moj9yzuju";
        //        String appSecret =
        // "oPlzb-72XCVbpLUUqB3sRNR2KQwxtqnbqi2bki_dGtN9Zi5jPWfy6qqtOyKZM2de";
        // 测试
        String appKey = "ding2qurqklmvjg5pxlm";
        String appSecret = "_PWdtl6GrmClZFu-Vn9nP7a9P1ri269BmEGHbMbxbKqyAM4rFXBczU1voinktphr";
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
        String str = Base64.encodeBase64String("HUMMINGBIRD_COLLEGE".getBytes());
        System.out.println(str);
    }

    public static void getDeptUserId() throws Exception {
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
        req.setDeptId("371909913");
        OapiUserGetDeptMemberResponse rsp = client.execute(req, getToken());
        List<String> userIds = rsp.getUserIds();
        System.out.println(JSON.toJSONString(userIds));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserByDept() throws Exception {
        long offset = 0L;
        boolean isFinish = false;
        List<OapiUserListbypageResponse.Userlist> userIds = Lists.newArrayList();
        Long[] depts = {375661504L};
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

        try {
            for (OapiUserListbypageResponse.Userlist userId : userIds) {
                Map<String, String> extMap = JSON.parseObject(userId.getExtattr(), Map.class);
                System.out.println(JSON.toJSONString(extMap));
            }
        } catch (Exception e) {
            System.out.println();
        }
    }

    @Test
    public void updateUser() throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/update");
        OapiUserUpdateRequest req = new OapiUserUpdateRequest();
        req.setUserid("046701565626198105");
        req.setExtattr("{\"骑士ID\":132105995953}");
        OapiUserUpdateResponse rsp = client.execute(req, getToken());
        System.out.println(rsp.getBody());
    }

    @Test
    public void test111() throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid("29475318131561622136");
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, getToken());
        String ext = response.getExtattr();
        int size = response.getDepartment().size();
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
        request.setId(358518191L);
        //        request.setName("临时部门15");
        request.setSourceIdentifier("TEMPORARY_GROUP_ROOT_0");
        OapiDepartmentUpdateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getDept() throws Exception {
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest req = new OapiDepartmentGetRequest();
        req.setId("378137633");
        req.setLang("zh_CN");
        req.setHttpMethod("GET");
        OapiDepartmentGetResponse rsp = client.execute(req, getToken());
        System.out.println(rsp.getBody());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test1111() throws Exception {
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, getToken());
        logger.info("allDepartment:{}", JSON.toJSONString(response.getDepartment()));

        for (OapiDepartmentListResponse.Department department : response.getDepartment()) {
            long offset = 0;
            boolean isFinish = false;
            Map<String, String> extMap = null;
            try {
                List<OapiUserListbypageResponse.Userlist> users = Lists.newArrayList();

                do {
                    DingTalkClient client2 =
                            new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
                    OapiUserListbypageRequest request2 = new OapiUserListbypageRequest();
                    request2.setDepartmentId(department.getId());
                    request2.setOffset(offset);
                    request2.setSize(100L);
                    OapiUserListbypageResponse response2 = client2.execute(request2, getToken());
                    users.addAll(response2.getUserlist());
                    offset += response2.getUserlist().size();
                    isFinish =
                            CollectionUtils.isEmpty(response2.getUserlist())
                                    || response2.getUserlist().size() == 0;
                } while (!isFinish);

                for (OapiUserListbypageResponse.Userlist user : users) {
                    String ext = user.getExtattr();

                    extMap = JSON.parseObject(ext, Map.class);
                }
            } catch (Exception e) {
                System.out.println(JSON.toJSONString(extMap));
            }
        }
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

        //        List<OapiDepartmentListResponse.Department> centerGroup = Lists.newArrayList();
        //        List<OapiDepartmentListResponse.Department> knightGroup = Lists.newArrayList();
        //        List<OapiDepartmentListResponse.Department> temporaryGroup = Lists.newArrayList();
        //        List<OapiDepartmentListResponse.Department> regionLogisticsGroup =
        // Lists.newArrayList();
        //
        //        RateLimiter limiter = RateLimiter.create(100);
        //        response.getDepartment().stream()
        //                .filter(Objects::nonNull)
        //                .filter(item -> !Strings.isNullOrEmpty(item.getSourceIdentifier()))
        //                .forEach(
        //                        item -> {
        //                            String sourceId = null;
        //                            if (item.getSourceIdentifier().contains("centerGroup")) {
        //                                centerGroup.add(item);
        //                                sourceId =
        // item.getSourceIdentifier().replace("centerGroup", "CENTER_GROUP");
        //                            } else if (item.getSourceIdentifier().contains("knightGroup"))
        // {
        //                                knightGroup.add(item);
        //                                sourceId =
        // item.getSourceIdentifier().replace("knightGroup", "KNIGHT_GROUP");
        //                            } else if
        // (item.getSourceIdentifier().contains("TEMPORARY_GROUP")) {
        //                                temporaryGroup.add(item);
        //                                sourceId =
        // item.getSourceIdentifier().replace("temporaryGroup", "TEMPORARY_GROUP");
        //                            } else if (item.getSourceIdentifier()
        //                                    .contains("regionLogisticsGroup")) {
        //                                regionLogisticsGroup.add(item);
        //                                sourceId =
        // item.getSourceIdentifier().replace("regionLogisticsGroup", "REGION_LOGISTICS_GROUP");
        //                            } else if
        // (item.getSourceIdentifier().contains("CUSTOMER_SERVICE")) {
        //                                System.out.println(JSON.toJSONString(item));
        //                            }
        //                            if (!Strings.isNullOrEmpty(sourceId)) {
        //                                limiter.acquire();
        //                                update(item.getId(), sourceId);
        //                            }
        //                        });
        //        System.out.println();
    }

    public void update(Long id, String sourceId) {
        try {
            AccessTokenDTO dto =
                    accessTokenHandler.getAccessToken(
                            crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
            DingTalkClient client =
                    new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
            OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
            request.setId(id);
            request.setSourceIdentifier(sourceId);
            OapiDepartmentUpdateResponse response = client.execute(request, dto.getAccessToken());
            System.out.println(JSON.toJSONString(response));
        } catch (Exception e) {

        }
    }
}
