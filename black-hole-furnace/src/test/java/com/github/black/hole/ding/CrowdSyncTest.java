package com.github.black.hole.ding;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiDepartmentCreateRequest;
import com.dingtalk.api.request.OapiDepartmentDeleteRequest;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationGetsendprogressRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationGetsendresultRequest;
import com.dingtalk.api.request.OapiUserDeleteRequest;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiDepartmentCreateResponse;
import com.dingtalk.api.response.OapiDepartmentDeleteResponse;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendprogressResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationGetsendresultResponse;
import com.dingtalk.api.response.OapiUserDeleteResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.github.black.hole.sboot.SbootMain;
import com.github.black.hole.sboot.ding.AccessTokenDTO;
import com.github.black.hole.sboot.ding.AccessTokenHandler;
import com.github.black.hole.sboot.ding.DingAppConfig;
import com.github.black.hole.sboot.ding.controller.CrowGroup;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.FileItem;
import org.assertj.core.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/6/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SbootMain.class)
public class CrowdSyncTest {

    private static Logger logger = LoggerFactory.getLogger(CrowdSyncTest.class);

    private static final List<String> position = Lists.newArrayList("骑手", "骑士");

    @Autowired DingAppConfig crowdOrgConfig;
    @Autowired AccessTokenHandler accessTokenHandler;

    @Autowired CrowGroup crowGroup;

    @Test
    public void test222() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient(
                        "https://oapi.dingtalk.com/topapi/message/corpconversation/getsendprogress");
        OapiMessageCorpconversationGetsendprogressRequest request =
                new OapiMessageCorpconversationGetsendprogressRequest();
        request.setAgentId(771858795L);
        request.setTaskId(276843799282L);
        OapiMessageCorpconversationGetsendprogressResponse response =
                client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));

        DingTalkClient client1 =
                new DefaultDingTalkClient(
                        "https://oapi.dingtalk.com/topapi/message/corpconversation/getsendresult");
        OapiMessageCorpconversationGetsendresultRequest request1 =
                new OapiMessageCorpconversationGetsendresultRequest();
        request1.setAgentId(771858795L);
        request1.setTaskId(771858795L);
        OapiMessageCorpconversationGetsendresultResponse response1 =
                client1.execute(request1, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response1));
    }

    @Test
    public void test111() throws Exception {
        //        crowGroup.statisticsOwner();
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());

        String filePath = "/Users/longhairen/Desktop/demo.xlsx";
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest request = new OapiMediaUploadRequest();
        request.setType("file");
        request.setMedia(new FileItem("无主管或群主不是主管的商圈.xlsx", bytes));
        OapiMediaUploadResponse response = client.execute(request, dto.getAccessToken());

        DingTalkClient client3 =
                new DefaultDingTalkClient(
                        "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request3 =
                new OapiMessageCorpconversationAsyncsendV2Request();
        request3.setUseridList("0948453840152451,296431220423633088");
        request3.setAgentId(771858795L);
        request3.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg =
                new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("file");
        msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
        msg.getFile().setMediaId(response.getMediaId());
        request3.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response3 =
                client3.execute(request3, dto.getAccessToken());
        logger.info(JSON.toJSONString(response3));
    }

    @Test
    public void getCallBack() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
        OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
        request.setHttpMethod("GET");
        OapiCallBackGetCallBackResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(response);
    }

    @Test
    public void updateAdmin() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client1 =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request1 = new OapiDepartmentListRequest();
        request1.setId("81721025");
        OapiDepartmentListResponse response1 = client1.execute(request1, dto.getAccessToken());
        List<Long> deptList =
                response1.getDepartment().stream()
                        .filter(item -> Objects.nonNull(item.getParentid()))
                        .map(OapiDepartmentListResponse.Department::getId)
                        .collect(Collectors.toList());
        Map<Long, OapiDepartmentListResponse.Department> departmentMap =
                response1.getDepartment().stream()
                        .collect(
                                Collectors.toMap(
                                        OapiDepartmentListResponse.Department::getId,
                                        Function.identity(),
                                        (v1, v2) -> v1));
        Map<String, Integer> departCount = Maps.newHashMap();
        departmentMap
                .entrySet()
                .forEach(
                        entry -> {
                            try {
                                long offset = 0L;
                                boolean isFinish = false;
                                List<String> userIds = Lists.newArrayList();
                                Long deptId = entry.getKey();
                                do {
                                    DingTalkClient client2 =
                                            new DefaultDingTalkClient(
                                                    "https://oapi.dingtalk.com/user/listbypage");
                                    OapiUserListbypageRequest request2 =
                                            new OapiUserListbypageRequest();
                                    request2.setDepartmentId(deptId);
                                    request2.setOffset(offset);
                                    request2.setSize(100L);
                                    OapiUserListbypageResponse response2 =
                                            client2.execute(request2, dto.getAccessToken());
                                    userIds.addAll(
                                            Optional.ofNullable(response2.getUserlist())
                                                    .orElse(Collections.emptyList()).stream()
                                                    .filter(Objects::nonNull)
                                                    .map(
                                                            OapiUserListbypageResponse.Userlist
                                                                    ::getUserid)
                                                    .collect(Collectors.toList()));
                                    offset += response2.getUserlist().size();
                                    isFinish =
                                            CollectionUtils.isEmpty(response2.getUserlist())
                                                    || response2.getUserlist().size() == 0;
                                } while (!isFinish);
                                String deptName = entry.getValue().getName();
                                String areaId =
                                        deptName.substring(
                                                deptName.lastIndexOf("(") + 1,
                                                deptName.lastIndexOf(")"));
                                departCount.put(areaId, userIds.size());
                            } catch (Exception e) {

                            }
                        });
        System.out.println(JSON.toJSONString(departCount));
    }

    @Test
    public void updateOutDept() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client1 =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request1 = new OapiDepartmentListRequest();
        //                request1.setId("370962589");
        OapiDepartmentListResponse response1 = client1.execute(request1, dto.getAccessToken());
        //        logger.info("allDepartment:{}", JSON.toJSONString(response1.getDepartment()));
        List<OapiDepartmentListResponse.Department> list =
                response1.getDepartment().stream()
                        .filter(
                                item ->
                                        item.getName().contains("咸阳")
                                                || item.getName().contains("银川"))
                        .collect(Collectors.toList());

        Map<Long, List<OapiDepartmentListResponse.Department>> departmentMap =
                response1.getDepartment().stream()
                        .filter(Objects::nonNull)
                        .filter(item -> Objects.nonNull(item.getParentid()))
                        .collect(
                                Collectors.groupingBy(
                                        OapiDepartmentListResponse.Department::getParentid));

        List<OapiDepartmentListResponse.Department> deptMap =
                response1.getDepartment().stream()
                        .filter(item -> !Objects.equals(item.getId(), 1L))
                        .filter(item -> Strings.isNullOrEmpty(item.getSourceIdentifier()))
                        .collect(Collectors.toList());
        deptMap.forEach(
                item -> {
                    try {
                        DingTalkClient client =
                                new DefaultDingTalkClient(
                                        "https://oapi.dingtalk.com/user/getDeptMember");
                        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
                        req.setDeptId(String.valueOf(item.getId()));
                        OapiUserGetDeptMemberResponse rsp =
                                client.execute(req, dto.getAccessToken());
                        List<String> userIds = rsp.getUserIds();
                        userIds.forEach(
                                userId -> {
                                    try {
                                        DingTalkClient client111 =
                                                new DefaultDingTalkClient(
                                                        "https://oapi.dingtalk.com/user/delete");
                                        OapiUserDeleteRequest request111 =
                                                new OapiUserDeleteRequest();
                                        request111.setUserid(userId);
                                        OapiUserDeleteResponse response111 =
                                                client111.execute(request111, dto.getAccessToken());
                                        System.out.println(response111);
                                        //                                        }
                                    } catch (Exception e) {

                                    }
                                });

                        DingTalkClient client22 =
                                new DefaultDingTalkClient(
                                        "https://oapi.dingtalk.com/department/delete");
                        OapiDepartmentDeleteRequest request = new OapiDepartmentDeleteRequest();
                        request.setId(item.getId().toString());
                        OapiDepartmentDeleteResponse response =
                                client22.execute(request, dto.getAccessToken());
                        System.out.println(response);
                    } catch (Exception e) {

                    }
                });
        //        response1.getDepartment().stream()
        //                .filter(item -> CollectionUtils.isEmpty(deptMap.get(item.getId())))
        //                .forEach(
        //                        item -> {
        //                            try {
        //                                DingTalkClient client =
        //                                        new DefaultDingTalkClient(
        //
        // "https://oapi.dingtalk.com/department/update");
        //                                OapiDepartmentUpdateRequest request =
        //                                        new OapiDepartmentUpdateRequest();
        //                                request.setId(item.getId());
        //                                request.setOuterDept(true);
        //                                OapiDepartmentUpdateResponse response =
        //                                        client.execute(request, dto.getAccessToken());
        //                                System.out.println(JSON.toJSONString(response));
        //                            } catch (Exception e) {
        //
        //                            }
        //                        });
    }

    @Test
    public void deleteInActiveUser() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client1 =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request1 = new OapiDepartmentListRequest();
        request1.setId("370962589");
        OapiDepartmentListResponse response1 = client1.execute(request1, dto.getAccessToken());
        logger.info("allDepartment:{}", JSON.toJSONString(response1.getDepartment()));
        Set<Long> departmentIds =
                response1.getDepartment().stream()
                        .map(OapiDepartmentListResponse.Department::getId)
                        .collect(Collectors.toSet());
        //        departmentIds.add(Long.valueOf("358518191"));

        departmentIds.forEach(
                deptId -> {
                    try {
                        DingTalkClient client =
                                new DefaultDingTalkClient(
                                        "https://oapi.dingtalk.com/user/getDeptMember");
                        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
                        req.setDeptId(String.valueOf(deptId));
                        OapiUserGetDeptMemberResponse rsp =
                                client.execute(req, dto.getAccessToken());
                        List<String> userIds = rsp.getUserIds();
                        userIds.forEach(
                                userId -> {
                                    try {
                                        //                                        DingTalkClient
                                        // client11 =
                                        //                                                new
                                        // DefaultDingTalkClient(
                                        //
                                        // "https://oapi.dingtalk.com/user/get");
                                        //                                        OapiUserGetRequest
                                        // request = new OapiUserGetRequest();
                                        //
                                        // request.setUserid(userId);
                                        //
                                        // OapiUserGetResponse response =
                                        //
                                        // client11.execute(request, dto.getAccessToken());
                                        //                                        Map<String,
                                        // String> extMap =
                                        //
                                        // getExtendMap(response.getExtattr());
                                        //                                        if
                                        // (!response.getActive()
                                        //                                                ||
                                        // CollectionUtils.isEmpty(extMap)) {
                                        DingTalkClient client111 =
                                                new DefaultDingTalkClient(
                                                        "https://oapi.dingtalk.com/user/delete");
                                        OapiUserDeleteRequest request111 =
                                                new OapiUserDeleteRequest();
                                        request111.setUserid(userId);
                                        OapiUserDeleteResponse response111 =
                                                client111.execute(request111, dto.getAccessToken());
                                        System.out.println(response111);
                                        //                                        }
                                    } catch (Exception e) {

                                    }
                                });
                    } catch (Exception e) {

                    }
                });
    }

    @Test
    public void getDepartDetail() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId("394693577");
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(response);
    }

    @Test
    public void update() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
        OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
        request.setId(375819363L);
        request.setDeptManagerUseridList("015948543229213071|04216211451049322");
        request.setOrgDeptOwner("04216211451049322");
        OapiDepartmentUpdateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
}

    @Test
    public void getUser() throws Exception {
        //        310304140538747822
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid("KNIGHT.1.123940649");
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, dto.getAccessToken());
    }

    @Test
    public void getUserByMobileTest() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
        OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
        request.setMobile("15296908736");

        OapiUserGetByMobileResponse response = client.execute(request, dto.getAccessToken());
        logger.info(JSON.toJSONString(response));

        DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request1 = new OapiUserGetRequest();
        request1.setUserid(response.getUserid());
        request1.setHttpMethod("GET");
        OapiUserGetResponse response1 = client1.execute(request1, dto.getAccessToken());
        logger.info(JSON.toJSONString(response1));
    }

    @Test
    public void test11121() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client1 = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request1 = new OapiUserGetRequest();
        request1.setUserid("035050052440349630");
        request1.setHttpMethod("GET");
        OapiUserGetResponse response1 = client1.execute(request1, dto.getAccessToken());
        logger.info(JSON.toJSONString(response1));
    }

    @Test
    public void createDepartment() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
        request.setParentid("1");
        //        request.setCreateDeptGroup(false);
        //        for (int i = 0; i < 10; i++) {
        request.setName("众包分层群组");
        request.setSourceIdentifier("CROWD_LAYERED_GROUP_ROOT_");
        OapiDepartmentCreateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
        //        }
    }

    @Test
    public void test() throws Exception {
        //        366565999
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId("358518191");
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(response.toString());
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

        //        Map<String, OapiDepartmentListResponse.Department> dingDeptMap =
        //                response.getDepartment().stream()
        //                        .filter(
        //                                dept ->
        //                                        !Strings.isNullOrEmpty(dept.getSourceIdentifier())
        //                                                && dept.getSourceIdentifier()
        //                                                        .contains("CROWD_DISTRICT"))
        //                        .collect(
        //                                Collectors.toConcurrentMap(
        //
        // OapiDepartmentListResponse.Department::getSourceIdentifier,
        //                                        Function.identity(),
        //                                        (v1, v2) -> v1));
        //        logger.info("allDepartment:{}", JSON.toJSONString(dingDeptMap));
        //
        //        for (OapiDepartmentListResponse.Department department : dingDeptMap.values()) {
        //            if (Objects.isNull(department.getParentid())) {
        //                System.out.println(department);
        //            }
        //        }
        //        System.out.println("deptMap");
    }

    @Test
    public void getAllDingDepartment() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        logger.info("allDepartment:{}", JSON.toJSONString(response.getDepartment()));

        Map<String, OapiDepartmentListResponse.Department> dingDeptMap =
                response.getDepartment().stream()
                        .filter(Objects::nonNull)
                        .collect(
                                Collectors.toMap(
                                        OapiDepartmentListResponse.Department::getSourceIdentifier,
                                        Function.identity(),
                                        (v1, v2) -> v1));
        logger.info("allDepartment:{}", JSON.toJSONString(dingDeptMap));
    }
}
