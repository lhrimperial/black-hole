package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentCreateRequest;
import com.dingtalk.api.request.OapiDepartmentDeleteRequest;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiDepartmentUpdateRequest;
import com.dingtalk.api.request.OapiUserCreateRequest;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiDepartmentCreateResponse;
import com.dingtalk.api.response.OapiDepartmentDeleteResponse;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiDepartmentUpdateResponse;
import com.dingtalk.api.response.OapiUserCreateResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.github.black.hole.ding.DingApplication;
import com.github.black.hole.ding.dto.DingAppConfig;
import com.github.black.hole.ding.handler.AccessTokenDTO;
import com.github.black.hole.ding.handler.AccessTokenHandler;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hairen.long
 * @date 2020/5/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DingApplication.class)
public class AccessTokenTest {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenTest.class);

    @Autowired DingAppConfig noticeConfig;
    @Autowired AccessTokenHandler accessTokenHandler;

    @Test
    public void createDepartment11() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        noticeConfig.getAppKey(), noticeConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
        request.setParentid("1");
        request.setName("众包分层群组测试");
        request.setSourceIdentifier("CROWD_LAYERED_GROUP_ROOT_");
        OapiDepartmentCreateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void createUser() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        noticeConfig.getAppKey(), noticeConfig.getAppSecret());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/create");
        OapiUserCreateRequest request = new OapiUserCreateRequest();
        //        request.setUserid("zhangsan");
        request.setMobile("15411111111");
        request.setName("李四");

        // 需要用字符串， "[100,200]" 这种格式
        List<Long> departments = new ArrayList<Long>();
        departments.add(341999323L);
        request.setDepartment(JSON.toJSONString(departments));

        OapiUserCreateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void deleteDepartment() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        noticeConfig.getAppKey(), noticeConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/delete");
        OapiDepartmentDeleteRequest request = new OapiDepartmentDeleteRequest();
        request.setId("1334");

        OapiDepartmentDeleteResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getDepartmentUser() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        "dinguenpya4moj9yzuju",
                        "oPlzb-72XCVbpLUUqB3sRNR2KQwxtqnbqi2bki_dGtN9Zi5jPWfy6qqtOyKZM2de");

        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
        OapiUserListbypageRequest request = new OapiUserListbypageRequest();
        request.setDepartmentId(366565999L);
        request.setOffset(0L);
        request.setSize(10L);
        request.setHttpMethod("GET");

        OapiUserListbypageResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void createDepartment() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        noticeConfig.getAppKey(), noticeConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
        request.setParentid("1");
        request.setCreateDeptGroup(true);
        request.setName("客服中心11");
        // id 355418389
        request.setSourceIdentifier("1000000000000");
        OapiDepartmentCreateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void updateDepartment() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        "ding2qurqklmvjg5pxlm",
                        "_PWdtl6GrmClZFu-Vn9nP7a9P1ri269BmEGHbMbxbKqyAM4rFXBczU1voinktphr");
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/update");
        OapiDepartmentUpdateRequest request = new OapiDepartmentUpdateRequest();
        request.setId(1L);
        request.setSourceIdentifier("CROWD_KNIGHT_");
        OapiDepartmentUpdateResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getDepartmentDetail() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        noticeConfig.getAppKey(), noticeConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId("1");
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(response.toString());
    }

    @Test
    public void getDepartmentList() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        "ding2qurqklmvjg5pxlm",
                        "_PWdtl6GrmClZFu-Vn9nP7a9P1ri269BmEGHbMbxbKqyAM4rFXBczU1voinktphr");

        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void getUserByMobileTest() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        "ding2qurqklmvjg5pxlm",
                        "_PWdtl6GrmClZFu-Vn9nP7a9P1ri269BmEGHbMbxbKqyAM4rFXBczU1voinktphr");
        //
        // {"agentId":"789887943","appKey":"ding2qurqklmvjg5pxlm","appSecret":"_PWdtl6GrmClZFu-Vn9nP7a9P1ri269BmEGHbMbxbKqyAM4rFXBczU1voinktphr"}
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
        OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
        request.setMobile("18621526765");

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
    public void test() {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        noticeConfig.getAppKey(), noticeConfig.getAppSecret());
        System.out.println(dto);
        System.out.println(58 + 24.25);
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DingTalkConfig {
        /** 钉钉应用 */
        private String agentId;
        /** 应用app key */
        private String appKey;
        /** 应用secret */
        private String appSecret;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChildDingTalkConfig extends DingTalkConfig {
        private String name;

        public static void main(String[] args) {
            ChildDingTalkConfig config =
                    ChildDingTalkConfig.builder()
                            .agentId("789887943")
                            .appKey("ding2qurqklmvjg5pxlm")
                            .appSecret(
                                    "_PWdtl6GrmClZFu-Vn9nP7a9P1ri269BmEGHbMbxbKqyAM4rFXBczU1voinktphr")
                            .build();
            System.out.println(JSON.toJSONString(config));
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserMapping {
        private String srcMobile;
        private String srcName;
        private String desMobile;
        private String desName;
    }

    public static void main(String[] args) {
        UserMapping mapping =
                UserMapping.builder()
                        .desMobile("18621526765")
                        .desName("zhangsan")
                        .srcMobile("13640599896")
                        .srcName("lisi")
                        .build();
        System.out.println(JSON.toJSONString(Lists.newArrayList(mapping)));
        System.out.println(Maps.newHashMap().toString());
    }
}
