package com.github.black.hole.sboot.ding.controller;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.github.black.hole.sboot.common.ServiceException;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.taobao.api.FileItem;
import lombok.Builder;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author hairen.long
 * @date 2020/10/23
 */
@Component
public class CrowGroup extends AbstractCrowd {

    private static Pattern PATTERN = Pattern.compile("(?<=\\()(.+?)(?=\\))");
    private static final String YES = "是";
    private static final String NO = "否";

    public void statisticsOwner() throws Exception {
        DingTalkClient client1 =
                new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
        OapiDepartmentListRequest request1 = new OapiDepartmentListRequest();
        OapiDepartmentListResponse response1 = client1.execute(request1, getToken());
        List<Long> deptList =
                response1.getDepartment().stream()
                        .filter(item -> Objects.nonNull(item.getParentid()))
                        .filter(
                                item ->
                                        !Strings.isNullOrEmpty(item.getSourceIdentifier())
                                                && item.getSourceIdentifier()
                                                        .contains("CROWD_DISTRICT"))
                        .map(OapiDepartmentListResponse.Department::getId)
                        .collect(Collectors.toList());
        List<ExportDTO> exports = Lists.newArrayList();
        deptList.forEach(
                deptId -> {
                    try {
                        DingTalkClient client2 =
                                new DefaultDingTalkClient(
                                        "https://oapi.dingtalk.com/department/get");
                        OapiDepartmentGetRequest request2 = new OapiDepartmentGetRequest();
                        request2.setId(String.valueOf(deptId));
                        OapiDepartmentGetResponse response2 = client2.execute(request2, getToken());
                        String managers = response2.getDeptManagerUseridList();
                        String owner = response2.getOrgDeptOwner();
                        String sourceId = response2.getSourceIdentifier();
                        Matcher matcher = PATTERN.matcher(sourceId);
                        String districtId = null;
                        while (matcher.find()) {
                            districtId = matcher.group();
                        }

                        boolean hasManager = !Strings.isNullOrEmpty(managers);
                        boolean ownerIsManager =
                                hasManager
                                        && !Strings.isNullOrEmpty(owner)
                                        && managers.contains(owner);
                        if (!hasManager || !ownerIsManager) {
                            exports.add(
                                    ExportDTO.builder()
                                            .deptId(deptId)
                                            .deptName(response2.getName())
                                            .districtId(districtId)
                                            .managers(hasManager ? YES : NO)
                                            .owner(ownerIsManager ? YES : NO)
                                            .build());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        byte[] bytes = generate(exports);
        //        OutputStream stream = new
        // FileOutputStream("/Users/longhairen/Desktop/data112222.xlsx");
        //        stream.write(bytes);

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/media/upload");
        OapiMediaUploadRequest request = new OapiMediaUploadRequest();
        request.setType("file");
        request.setMedia(new FileItem("无主管或群主不是主管的商圈.xlsx", bytes));
        OapiMediaUploadResponse response = client.execute(request, getToken());

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
                client3.execute(request3, getToken());
        logger.info(JSON.toJSONString(response3));
    }

    public byte[] generate(List<ExportDTO> exports) throws ServiceException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("无主管或群主不是主管的商圈");
        XSSFRow header = sheet.createRow(0);

        List<String> headers = Lists.newArrayList("部门ID", "部门名称", "商圈ID", "是否有主管", "群主是否是主管");
        for (int i = 0, len = headers.size(); i < len; i++) {
            header.createCell(i).setCellValue(headers.get(i));
        }
        for (int i = 0, len = exports.size(); i < len; i++) {
            ExportDTO data = exports.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(data.getDeptId());
            row.createCell(1).setCellValue(data.getDeptName());
            row.createCell(2).setCellValue(data.getDistrictId());
            row.createCell(3).setCellValue(data.getManagers());
            row.createCell(4).setCellValue(data.getOwner());
        }
        return generateByteArr(workbook);
    }

    public static byte[] generateByteArr(XSSFWorkbook workbook) throws ServiceException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            throw new ServiceException("生成excel出错", e);
        } finally {
            try {
                os.close();
            } catch (IOException ignored) {
            }
        }
        return os.toByteArray();
    }

    @Data
    @Builder
    public static class ExportDTO {
        private Long deptId;
        private String deptName;
        private String managers;
        private String owner;
        private String districtId;
    }
}
