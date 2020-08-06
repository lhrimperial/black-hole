package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentCreateRequest;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.request.OapiUserDeleteRequest;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.request.OapiUserUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentCreateResponse;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.dingtalk.api.response.OapiUserDeleteResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.github.black.hole.ding.DingApplication;
import com.github.black.hole.ding.dto.DingAppConfig;
import com.github.black.hole.ding.handler.AccessTokenDTO;
import com.github.black.hole.ding.handler.AccessTokenHandler;
import com.github.black.hole.excel.DemoData;
import com.github.black.hole.util.ExcelReadUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.ApiException;
import org.assertj.core.util.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/6/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DingApplication.class)
public class CrowdSyncTest {

    private static Logger logger = LoggerFactory.getLogger(CrowdSyncTest.class);

    private static final List<String> position = Lists.newArrayList("骑手", "骑士");

    @Autowired DingAppConfig crowdOrgConfig;
    @Autowired AccessTokenHandler accessTokenHandler;

    private Map<String, String> getExtendMap(String extattr) {
        if (com.google.common.base.Strings.isNullOrEmpty(extattr)) {
            return Maps.newHashMap();
        }
        Map<String, String> extattrMap = Maps.newHashMap();
        String ext = extattr.substring(1, extattr.length() - 1);
        String[] extArr = ext.split(",");
        if (extArr.length > 0) {
            Arrays.stream(extArr)
                    .forEach(
                            item -> {
                                String[] temp = item.split("=");
                                if (temp.length == 2) {
                                    extattrMap.put(temp[0].trim(), temp[1].trim());
                                }
                            });
        }
        return extattrMap;
    }

    @Test
    public void updateAdmin() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client1 =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request1 = new OapiDepartmentListRequest();
        OapiDepartmentListResponse response1 = client1.execute(request1, dto.getAccessToken());
        List<Long> deptList =
                response1.getDepartment().stream()
                        .filter(item -> Objects.nonNull(item.getParentid()))
                        .map(OapiDepartmentListResponse.Department::getId)
                        .collect(Collectors.toList());
        deptList.forEach(
                deptId -> {
                    try {
                        long offset = 0L;
                        boolean isFinish = false;
                        List<String> userIds = Lists.newArrayList();
                        do {
                            DingTalkClient client2 =
                                    new DefaultDingTalkClient(
                                            "https://oapi.dingtalk.com/user/listbypage");
                            OapiUserListbypageRequest request2 = new OapiUserListbypageRequest();
                            request2.setDepartmentId(deptId);
                            request2.setOffset(offset);
                            request2.setSize(100L);
                            OapiUserListbypageResponse response2 =
                                    client2.execute(request2, dto.getAccessToken());
                            userIds.addAll(
                                    Optional.ofNullable(response2.getUserlist())
                                            .orElse(Collections.emptyList()).stream()
                                            .filter(Objects::nonNull)
                                            .filter(item -> !position.contains(item.getPosition()))
                                            .map(OapiUserListbypageResponse.Userlist::getUserid)
                                            .collect(Collectors.toList()));
                            offset += response2.getUserlist().size();
                            isFinish =
                                    CollectionUtils.isEmpty(response2.getUserlist())
                                            || response2.getUserlist().size() == 0;
                        } while (!isFinish);

                        userIds.forEach(
                                userId -> {
                                    try {
                                        DingTalkClient client =
                                                new DefaultDingTalkClient(
                                                        "https://oapi.dingtalk.com/user/update");
                                        OapiUserUpdateRequest request = new OapiUserUpdateRequest();
                                        request.setUserid(userId);
                                        request.setIsSenior(Boolean.TRUE);
                                        request.setIsHide(Boolean.TRUE);

                                        OapiUserUpdateResponse response =
                                                client.execute(request, dto.getAccessToken());
                                        System.out.println(response);
                                    } catch (Exception e) {

                                    }
                                });
                    } catch (Exception e) {

                    }
                });
    }

    @Test
    public void updateOutDept() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client1 =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request1 = new OapiDepartmentListRequest();
        //        request1.setId("91171117");
        OapiDepartmentListResponse response1 = client1.execute(request1, dto.getAccessToken());
        //        logger.info("allDepartment:{}", JSON.toJSONString(response1.getDepartment()));
        Map<Long, List<OapiDepartmentListResponse.Department>> deptMap =
                response1.getDepartment().stream()
                        .filter(item -> Objects.nonNull(item.getParentid()))
                        .collect(
                                Collectors.groupingBy(
                                        OapiDepartmentListResponse.Department::getParentid));
        response1.getDepartment().stream()
                .filter(item -> CollectionUtils.isEmpty(deptMap.get(item.getId())))
                .forEach(
                        item -> {
                            try {
                                DingTalkClient client =
                                        new DefaultDingTalkClient(
                                                "https://oapi.dingtalk.com/department/update");
                                OapiDepartmentUpdateRequest request =
                                        new OapiDepartmentUpdateRequest();
                                request.setId(item.getId());
                                request.setOuterDept(true);
                                OapiDepartmentUpdateResponse response =
                                        client.execute(request, dto.getAccessToken());
                                System.out.println(JSON.toJSONString(response));
                            } catch (Exception e) {

                            }
                        });
    }

    @Test
    public void deleteInActiveUser() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client1 =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request1 = new OapiDepartmentListRequest();
        //        request1.setId("358518191");
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
        request.setId("379377880");
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
        request.setId(102328160L);
        request.setParentid("373777961");
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
        request.setId("371568773");
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
        request.setId("81721035");
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        logger.info("allDepartment:{}", JSON.toJSONString(response.getDepartment()));

        Map<String, OapiDepartmentListResponse.Department> dingDeptMap =
                response.getDepartment().stream()
                        .filter(
                                dept ->
                                        !com.google.common.base.Strings.isNullOrEmpty(
                                                dept.getSourceIdentifier()))
                        .collect(
                                Collectors.toConcurrentMap(
                                        OapiDepartmentListResponse.Department::getSourceIdentifier,
                                        Function.identity(),
                                        (v1, v2) -> v1));
        logger.info("allDepartment:{}", JSON.toJSONString(dingDeptMap));

        for (OapiDepartmentListResponse.Department department : dingDeptMap.values()) {
            if (Objects.isNull(department.getParentid())) {
                System.out.println(department);
            }
        }
        System.out.println("deptMap");
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

    @Test
    public void updateDepartSourceId() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        logger.info("allDepartment:{}", JSON.toJSONString(response));

        Map<String, OapiDepartmentListResponse.Department> dingDeptMap =
                response.getDepartment().stream()
                        .filter(Objects::nonNull)
                        .collect(
                                Collectors.toMap(
                                        OapiDepartmentListResponse.Department::getName,
                                        Function.identity(),
                                        (v1, v2) -> v1));
        String filePath = "/Users/longhairen/Downloads/ddy_area2020-06-16(1).xlsx";
        List<DemoData> areas = ExcelReadUtil.readObject(filePath, DemoData.class);
        areas.forEach(
                area -> {
                    if (Objects.equals(area.getLevel(), 3)) {
                        area.setName(area.getName() + "(" + area.getAreaId() + ")");
                    }
                });
        Map<String, DemoData> areaMap =
                areas.stream()
                        .collect(
                                Collectors.toMap(
                                        DemoData::getName, Function.identity(), (v1, v2) -> v1));

        List<OapiDepartmentListResponse.Department> notMatchDepartments =
                dingDeptMap.entrySet().stream()
                        .filter(
                                item -> {
                                    String dingName = item.getKey();
                                    return !areaMap.keySet().contains(dingName);
                                })
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());
        System.out.println(notMatchDepartments);

        dingDeptMap
                .entrySet()
                .forEach(
                        entry -> {
                            DemoData area = areaMap.get(entry.getKey());
                            String sourceIdentifier = null;
                            if (Objects.nonNull(area)) {
                                switch (area.getLevel()) {
                                    case 1:
                                        sourceIdentifier = "CROWD_WAR_ZONE_" + area.getAreaId();
                                        break;
                                    case 2:
                                        sourceIdentifier = "CROWD_CITY_" + area.getAreaId();
                                        break;
                                    case 3:
                                        sourceIdentifier = "CROWD_DISTRICT_" + area.getAreaId();
                                        break;
                                    default:
                                        sourceIdentifier = UUID.randomUUID().toString();
                                }
                            }
                            if (Strings.isNullOrEmpty(sourceIdentifier)) {
                                sourceIdentifier = UUID.randomUUID().toString();
                            }
                            OapiDepartmentListResponse.Department department = entry.getValue();
                            department.setSourceIdentifier(sourceIdentifier);

                            OapiDepartmentUpdateRequest updateRequest =
                                    new OapiDepartmentUpdateRequest();
                            updateRequest.setId(department.getId());
                            updateRequest.setSourceIdentifier(department.getSourceIdentifier());
                            DingTalkClient updateClient =
                                    new DefaultDingTalkClient(
                                            "https://oapi.dingtalk.com/department/update");
                            try {
                                OapiDepartmentUpdateResponse updateResponse =
                                        updateClient.execute(updateRequest, dto.getAccessToken());
                                if (!updateResponse.isSuccess()) {
                                    logger.info(
                                            "update fail,response={}",
                                            JSON.toJSONString(updateResponse));
                                }
                            } catch (ApiException e) {
                                logger.error("error", e);
                            }
                        });
    }
}
