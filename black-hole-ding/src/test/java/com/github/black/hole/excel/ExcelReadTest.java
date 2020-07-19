package com.github.black.hole.excel;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentCreateRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiDepartmentCreateResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.github.black.hole.AccessTokenTest;
import com.github.black.hole.ding.DingApplication;
import com.github.black.hole.ding.dto.DingAppConfig;
import com.github.black.hole.ding.handler.AccessTokenDTO;
import com.github.black.hole.ding.handler.AccessTokenHandler;
import com.github.black.hole.util.ExcelReadUtil;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/5/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DingApplication.class)
public class ExcelReadTest {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenTest.class);

    @Autowired DingAppConfig crowdOrgConfig;
    @Autowired AccessTokenHandler accessTokenHandler;

    @Test
    public void getUserByMobileTest()throws Exception{
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
        OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
        request.setMobile("13611122433");

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
    public void testCreateDepartment()throws Exception{
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/create");
        OapiDepartmentCreateRequest request = new OapiDepartmentCreateRequest();
        request.setParentid("1");
//        request.setCreateDeptGroup(true);
        request.setName("临时部门");
        request.setOuterDeptOnlySelf(Boolean.TRUE);
        request.setOuterDept(Boolean.TRUE);
        //id 355418389
        request.setSourceIdentifier("temporaryGroup_");
        OapiDepartmentCreateResponse response = client.execute(request,dto.getAccessToken());
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testGreyAgency() throws Exception {
        String path = "/Users/longhairen/Downloads/灰侧测算计划表.xlsx";
        List<GreyAgency> areas = ExcelReadUtil.readObject(path, 2, GreyAgency.class);
        System.out.println(areas);
    }

    @Test
    public void testDdyUser() throws Exception {
        String path = "/Users/longhairen/Downloads/配送经理设置数据.xlsx";
        List<DdyUser> areas = ExcelReadUtil.readObject(path, DdyUser.class);
        System.out.println(areas);
    }

    @Test
    public void testReadExcel() throws Exception {
        String path = "/Users/longhairen/Downloads/ddy_area2020-05-21.xlsx";
        List<DemoData> areas = ExcelReadUtil.readObject(path, DemoData.class);
        System.out.println(areas);
    }

    @Test
    public void diffDepartment() throws Exception {
        AccessTokenDTO dto =
                accessTokenHandler.getAccessToken(
                        crowdOrgConfig.getAppKey(), crowdOrgConfig.getAppSecret());
        DingTalkClient client =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request = new OapiDepartmentListRequest();
        request.setHttpMethod("GET");
        OapiDepartmentListResponse response = client.execute(request, dto.getAccessToken());
        Map<String, OapiDepartmentListResponse.Department> dingDeptMap =
                response.getDepartment().stream()
                        .filter(Objects::nonNull)
                        .collect(
                                Collectors.toMap(
                                        OapiDepartmentListResponse.Department::getName,
                                        Function.identity(),
                                        (v1, v2) -> v1));
        List<DemoData> areas = readArea();
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

        List<OapiDepartmentListResponse.Department> departments =
                dingDeptMap.entrySet().stream()
                        .filter(
                                item -> {
                                    String dingName = item.getKey();
                                    DemoData demoData = areaMap.get(dingName);
                                    if (Objects.equals("无锡江阴市林达商圈(16362)", dingName)) {
                                        System.out.println(dingName);
                                    }
                                    return !areaMap.keySet().contains(dingName);
                                })
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toList());
        System.out.println(departments);
    }

    public static List<DemoData> readArea() throws Exception {
        String path = "/Users/longhairen/Downloads/ddy_area2020-05-21.xlsx";
        Workbook workbook = ExcelReadUtil.readWorkbook(path);
        Sheet sheet = workbook.getSheetAt(0);
        List<DemoData> dataList = Lists.newArrayList();
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            int cellIndex = row.getFirstCellNum();
            dataList.add(
                    DemoData.builder()
                            .areaId(Long.valueOf(row.getCell(cellIndex++).getStringCellValue()))
                            .name(row.getCell(cellIndex++).getStringCellValue())
                            .pid(Long.valueOf(row.getCell(cellIndex++).getStringCellValue()))
                            .level(Integer.valueOf(row.getCell(cellIndex++).getStringCellValue()))
                            .build());
        }
        return dataList;
    }
}
