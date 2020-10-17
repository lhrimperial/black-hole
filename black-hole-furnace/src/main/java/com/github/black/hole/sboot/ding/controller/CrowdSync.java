package com.github.black.hole.sboot.ding.controller;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.request.OapiUserUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.github.black.hole.sboot.ding.DingConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Strings;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/10/15
 */
@Component
public class CrowdSync {

    private Logger logger = LoggerFactory.getLogger(CrowdSync.class);
    private AccessToken accessToken;


    private static Map<String, String> getExtendMap(String extattr) {
        if (Strings.isNullOrEmpty(extattr)) {
            return Maps.newHashMap();
        }
        Map<String, String> extattrMap = Maps.newHashMap();
        String ext = extattr.substring(1, extattr.length() - 1);
        String[] extArr = ext.split(",");
        if (extArr.length > 0) {
            Arrays.stream(extArr)
                    .forEach(
                            item -> {
                                String[] temp = item.split(":");
                                if (temp.length == 2) {
                                    extattrMap.put(temp[0].trim().replace("\"",""), temp[1].trim().replace("\"",""));
                                }
                            });
        }
        return extattrMap;
    }

    private String getToken() throws Exception {
        DefaultDingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dinguenpya4moj9yzuju");
        request.setAppsecret("oPlzb-72XCVbpLUUqB3sRNR2KQwxtqnbqi2bki_dGtN9Zi5jPWfy6qqtOyKZM2de");
        request.setHttpMethod(DingConstants.GET);
        OapiGettokenResponse response = client.execute(request);
        return response.getAccessToken();
    }

    public void moveUser() throws Exception {
        accessToken =
                AccessToken.builder()
                        .accessToken(getToken())
                        .expireTime(System.currentTimeMillis())
                        .build();

        Map<String, OapiDepartmentListResponse.Department> allDept =
                getAllDistrictDepartment(accessToken);

        Long parentTemp = 358518191L;
        List<Long> temps =
                getChildrenDepartment(parentTemp, accessToken).stream()
                        .filter(item -> Objects.nonNull(item.getParentid()))
                        .filter(item -> Objects.equals(parentTemp, item.getParentid()))
                        .map(OapiDepartmentListResponse.Department::getId)
                        .collect(Collectors.toList());
        temps.add(parentTemp);
        temps.forEach(deptId->{
            try {
                List<String> userIds = getUserIdByDepartment(deptId, accessToken);
                userIds.parallelStream().forEach(userId -> {
                    try {
                        OapiUserGetResponse response = getUserDetail(userId, accessToken);
                        Map<String, String> extMap = getExtendMap(response.getExtattr());
                        String deptName = extMap.get("常驻商圈");
                        OapiDepartmentListResponse.Department dept = allDept.get(deptName);
                        if (Objects.nonNull(dept)) {
                            updateUser(response, dept.getId(), accessToken);
                        }
                    } catch (Exception e) {

                    }
                });
            }catch (Exception e){

            }
        });


        System.out.println(temps);
    }

    public void updateUser(OapiUserGetResponse response, Long deptId, AccessToken dto)
            throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/update");
        OapiUserUpdateRequest request = new OapiUserUpdateRequest();
        request.setUserid(response.getUserid());
        request.setDepartment(Lists.newArrayList(deptId));
        OapiUserUpdateResponse response1 = client.execute(request, dto.getAccessToken());
        logger.info(JSON.toJSONString(response1));
        if (response1.isSuccess()) {
            return;
        }
        if (Objects.equals(response1.getErrorCode(), "40001")
                || Objects.equals(response1.getErrorCode(), "40014")) {
            accessToken =
                    AccessToken.builder()
                            .accessToken(getToken())
                            .expireTime(System.currentTimeMillis())
                            .build();
            client.execute(request, accessToken.getAccessToken());
        }
    }

    public OapiUserGetResponse getUserDetail(String userId, AccessToken dto) throws Exception {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        return client.execute(request, dto.getAccessToken());
    }

    public List<String> getUserIdByDepartment(Long deptId, AccessToken dto) throws Exception {
        long offset = 0L;
        boolean isFinish = false;
        List<String> userIds = Lists.newArrayList();
        do {
            DingTalkClient client2 =
                    new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
            OapiUserListbypageRequest request2 = new OapiUserListbypageRequest();
            request2.setDepartmentId(deptId);
            request2.setOffset(offset);
            request2.setSize(100L);
            OapiUserListbypageResponse response2 = client2.execute(request2, dto.getAccessToken());
            userIds.addAll(
                    Optional.ofNullable(response2.getUserlist()).orElse(Collections.emptyList())
                            .stream()
                            .filter(Objects::nonNull)
                            .map(OapiUserListbypageResponse.Userlist::getUserid)
                            .collect(Collectors.toList()));
            offset += response2.getUserlist().size();
            isFinish =
                    CollectionUtils.isEmpty(response2.getUserlist())
                            || response2.getUserlist().size() == 0;
        } while (!isFinish);
        return userIds;
    }

    private List<OapiDepartmentListResponse.Department> getChildrenDepartment(
            Long parentId, AccessToken dto) throws Exception {
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(String.valueOf(parentId));
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        return response.getDepartment();
    }

    private Map<String, OapiDepartmentListResponse.Department> getAllDistrictDepartment(
            AccessToken dto) throws Exception {
        Pattern pattern = Pattern.compile("(?<=\\()(.+?)(?=\\))");
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId("1");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());

        return response.getDepartment().stream()
                .filter(item -> !Strings.isNullOrEmpty(item.getSourceIdentifier()))
                .filter(
                        item ->
                                !item.getSourceIdentifier().contains("CROWD_LAYERED_GROUP")
                                        && !item.getSourceIdentifier()
                                                .contains("CROWD_LAYERED_GROUP_ROOT"))
                .filter(item -> pattern.matcher(item.getName()).find())
                .collect(
                        Collectors.toMap(
                                item -> getKey(item), Function.identity(), (v1, v2) -> v1));
    }

    private String getKey(OapiDepartmentListResponse.Department dept) {
        String name = dept.getName();
        return name.substring(0, name.indexOf("("));
    }



    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccessToken {
        /** 授权码 */
        private String accessToken;
        /** 过期时间 */
        private Long expireTime;
    }
}
