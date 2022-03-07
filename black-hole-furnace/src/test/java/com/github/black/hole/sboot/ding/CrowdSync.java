package com.github.black.hole.sboot.ding;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListIdsRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserCreateRequest;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.request.OapiUserUpdateRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListIdsResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserCreateResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.dingtalk.api.response.OapiUserUpdateResponse;
import com.github.black.hole.sboot.SbootMain;
import com.github.black.hole.sboot.excel.DemoData;
import com.github.black.hole.sboot.excel.ExcelCellStyle;
import com.github.black.hole.sboot.excel.ExcelSheet;
import com.github.black.hole.sboot.excel.ExcelUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.RateLimiter;
import com.taobao.api.ApiException;
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

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Autowired
    DingAppConfig crowdOrgConfig;
    @Autowired
    AccessTokenHandler accessTokenHandler;

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

    @Test
    public void getChildrenDept() throws Exception {

        String url = "https://oapi.dingtalk.com/department/list";
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setId(String.valueOf(358518191));
        OapiDepartmentListResponse response =
                new DefaultDingTalkClient(url).execute(request, getToken());
        System.out.println(response);
    }

    @Test
    public void getDept() throws Exception {
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(String.valueOf(371909913));
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetResponse response = client.execute(request, getToken());
    }

    @Test
    public void createUserV2() throws Exception {
//        OapiV2UserCreateRequest request = new OapiV2UserCreateRequest();
//        request.setEmail("");
//        request.setMobile("15288462375");
//        request.setName("李自林");
//        request.setDeptIdList("588905765");
//        request.setTitle("骑士");
//        request.setExtension(
//                "{\"最后跑单时间\":\"2022-01-19 19:52:46\",\"七天有效完成单\":\"是\",\"骑士ID\":\"142631138\",\"常驻商圈\":\"昆明五华区高新商圈（快送）\",\"最近七天出勤\":\"7\"}");
//        request.setHideMobile(Boolean.TRUE);
//        request.setSeniorMode(Boolean.TRUE);
//        DingTalkClient client =
//                new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/create");
//        OapiV2UserCreateResponse response = client.execute(request, getToken());
//        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void createUser() throws Exception {
        OapiUserCreateRequest request = new OapiUserCreateRequest();
        request.setEmail("");
        request.setMobile("17692168000");
        request.setName("展示");
        request.setPosition("骑士");
        request.setDepartment("[1]");
        String url = "https://oapi.dingtalk.com/user/create";
        DingTalkClient client = new DefaultDingTalkClient(url);
        OapiUserCreateResponse response = client.execute(request, getToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getUserById() throws Exception {
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid("01020121465829515687");
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetResponse response = client.execute(request, getToken());
        System.out.println(response);
    }

    @Test
    public void getSubIds() throws Exception {
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId("1");
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsResponse response = client.execute(request, getToken());
        System.out.println(JSON.toJSONString(response));
    }
}
