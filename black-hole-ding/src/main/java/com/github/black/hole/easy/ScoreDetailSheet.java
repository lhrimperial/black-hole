package com.github.black.hole.easy;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author huanglongping
 * @date 2020/7/2
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDetailSheet extends BaseSheet {

    /** 大区名称 */
    @ExcelProperty(
            value = {"大区", "权重"},
            index = 0)
    private String buName;
    /** 城市名称 */
    @ExcelProperty(
            value = {"城市", "权重"},
            index = 1)
    private String cityName;
    /** 代理商id */
    @ExcelProperty(
            value = {"代理商ID", "权重"},
            index = 2)
    private Long agencyId;
    /** 代理商名称 */
    @ExcelProperty(
            value = {"代理商名称", "权重"},
            index = 3)
    private String agencyName;
    /** 站点i */
    @ExcelProperty(
            value = {"站点ID", "权重"},
            index = 4)
    private Long teamId;
    /** 站点名称 */
    @ExcelProperty(
            value = {"站点名称", "权重"},
            index = 5)
    private String teamName;
    /** 等级 */
    @ExcelProperty(
            value = {"等级", "权重"},
            index = 6)
    private String level;
    /** 排名 */
    @ExcelProperty(
            value = {"排名", "权重"},
            index = 7)
    private Integer rank;
    /** 总得分 */
    @ExcelProperty(
            value = {"总得分", "权重"},
            index = 8)
    private String totalScore;
    /** 非奖惩KPI */
    @ExcelProperty(
            value = {"非奖惩KPI", "非奖惩KPI得分"},
            index = 9)
    private String noSanctionKpi;
    /** 非奖惩KPI最终得分 */
    @ExcelProperty(
            value = {"非奖惩KPI最终得分", "非奖惩KPI得分"},
            index = 10)
    private String noSanctionKpiScore;
    /** 30日活跃骑手留存率 */
    @ExcelProperty(
            value = {"30日活跃骑手留存率", "30日活跃骑手留存率"},
            index = 11)
    private String activeKnightExistRate;
    /** 30日活跃骑手留存率最终得分 */
    @ExcelProperty(
            value = {"30日活跃骑手留存率最终得分", "30日活跃骑手留存率"},
            index = 12)
    private String activeKnightExistRateScore;
    /** 排班出勤率 */
    @ExcelProperty(
            value = {"排班出勤率", "排班出勤率"},
            index = 13)
    private String knightAttendanceRate;
    /** 排班出勤率最终得分 */
    @ExcelProperty(
            value = {"排班出勤率最终得分", "排班出勤率"},
            index = 14)
    private String knightAttendanceRateScore;
    /** 商户进线投诉率 */
    @ExcelProperty(
            value = {"商户进线投诉率", "商户进线投诉率"},
            index = 15)
    private String retailerComplainRate;
    /** 商户进线投诉率最终得分 */
    @ExcelProperty(
            value = {"商户进线投诉率最终得分", "商户进线投诉率"},
            index = 16)
    private String retailerComplainRateScore;
    /** 消费者进线投诉率 */
    @ExcelProperty(
            value = {"消费者进线投诉率", "消费者进线投诉率"},
            index = 17)
    private String consumerComplainRate;
    /** 消费者进线投诉率最终得分 */
    @ExcelProperty(
            value = {"消费者进线投诉率最终得分", "消费者进线投诉率"},
            index = 18)
    private String consumerComplainRateScore;
    /** 虚假报备订单占比 */
    @ExcelProperty(
            value = {"虚假报备订单占比", "虚假报备订单占比"},
            index = 19)
    private String cheatingReportRate;
    /** 虚假报备订单占比最终得分 */
    @ExcelProperty(
            value = {"虚假报备订单占比最终得分", "虚假报备订单占比"},
            index = 20)
    private String cheatingReportRateScore;
    /** 违规操作率 */
    @ExcelProperty(
            value = {"违规操作率", "违规操作率"},
            index = 21)
    private String violationRate;
    /** 违规操作率最终得分 */
    @ExcelProperty(
            value = {"违规操作率最终得分", "违规操作率"},
            index = 22)
    private String violationRateScore;
    /** 骑手进线投诉率 */
    @ExcelProperty(
            value = {"骑手进线投诉率", "骑手进线投诉率"},
            index = 23)
    private String knightComplainRate;
    /** 骑手进线投诉率最终得分 */
    @ExcelProperty(
            value = {"骑手进线投诉率最终得分", "骑手进线投诉率"},
            index = 24)
    private String knightComplainRateScore;
    /** 晨会召开率 */
    @ExcelProperty(
            value = {"晨会召开率", "晨会召开率"},
            index = 25)
    private String meetingRate;
    /** 晨会召开率最终得分 */
    @ExcelProperty(
            value = {"晨会召开率最终得分", "晨会召开率"},
            index = 26)
    private String meetingRateScore;
    /** QC装备抽检合格率 */
    @ExcelProperty(
            value = {"QC装备抽检合格率", "QC装备抽检合格率"},
            index = 27)
    private String equipmentQualifiedRate;
    /** QC装备抽检合格率最终得分 */
    @ExcelProperty(
            value = {"QC装备抽检合格率最终得分", "QC装备抽检合格率"},
            index = 28)
    private String equipmentQualifiedRateScore;
    /** 业务考试 */
    @ExcelProperty(
            value = {"业务考试", "业务考试"},
            index = 29)
    private String examination;
    /** 业务考试最终得分 */
    @ExcelProperty(
            value = {"业务考试最终得分", "业务考试"},
            index = 30)
    private String examinationScore;
    /** 站长参训合格率 */
    @ExcelProperty(
            value = {"站长参训合格率", "站长参训合格率"},
            index = 31)
    private String trainQualifiedRate;
    /** 站长参训合格率最终得分 */
    @ExcelProperty(
            value = {"站长参训合格率最终得分", "站长参训合格率"},
            index = 32)
    private String trainQualifiedRateScore;
    /** 骑手安全管理 */
    @ExcelProperty(
            value = {"骑手安全管理", "骑手安全管理"},
            index = 33)
    private String accidentManager;
    /** 骑手安全管理最终得分 */
    @ExcelProperty(
            value = {"骑手安全管理最终得分", "骑手安全管理"},
            index = 34)
    private String accidentManagerScore;
    /** 高峰运力波动次数 */
    @ExcelProperty(
            value = {"高峰运力波动次数", "高峰运力波动次数"},
            index = 35)
    private String capacityFluctuateCount;
    /** 高峰运力波动次数最终得分 */
    @ExcelProperty(
            value = {"高峰运力波动次数最终得分", "高峰运力波动次数"},
            index = 36)
    private String capacityFluctuateCountScore;

    /**
     * 指标权重展示需要，动态生成表头
     *
     * @param indexWeightMap
     * @return
     */
    public static List<List<String>> sheetHead(Map<String, String> indexWeightMap) {
        List<List<String>> heads = new ArrayList<>();
        heads.add(Arrays.asList("大区", "权重"));
        heads.add(Arrays.asList("城市", "权重"));
        heads.add(Arrays.asList("代理商ID", "权重"));
        heads.add(Arrays.asList("代理商名称", "权重"));
        heads.add(Arrays.asList("站点ID", "权重"));
        heads.add(Arrays.asList("站点名称", "权重"));
        heads.add(Arrays.asList("等级", "权重"));
        heads.add(Arrays.asList("排名", "权重"));
        heads.add(Arrays.asList("总得分", "权重"));
        heads.add(
                Arrays.asList(
                        "非奖惩KPI", "非奖惩KPI得分权重占" + indexWeightMap.getOrDefault("非奖惩KPI得分", "")));
        heads.add(
                Arrays.asList(
                        "非奖惩KPI最终得分", "非奖惩KPI得分权重占" + indexWeightMap.getOrDefault("非奖惩KPI得分", "")));
        heads.add(
                Arrays.asList(
                        "30日活跃骑手留存率",
                        "30日活跃骑手留存率权重占" + indexWeightMap.getOrDefault("30日活跃骑手留存率", "")));
        heads.add(
                Arrays.asList(
                        "30日活跃骑手留存率最终得分",
                        "30日活跃骑手留存率权重占" + indexWeightMap.getOrDefault("30日活跃骑手留存率", "")));
        heads.add(Arrays.asList("排班出勤率", "排班出勤率权重占" + indexWeightMap.getOrDefault("排班出勤率", "")));
        heads.add(
                Arrays.asList("排班出勤率最终得分", "排班出勤率权重占" + indexWeightMap.getOrDefault("排班出勤率", "")));
        heads.add(
                Arrays.asList(
                        "商户进线投诉率", "商户进线投诉率权重占" + indexWeightMap.getOrDefault("商户进线投诉率", "")));
        heads.add(
                Arrays.asList(
                        "商户进线投诉率最终得分", "商户进线投诉率权重占" + indexWeightMap.getOrDefault("商户进线投诉率", "")));
        heads.add(
                Arrays.asList(
                        "消费者进线投诉率", "消费者进线投诉率权重占" + indexWeightMap.getOrDefault("消费者进线投诉率", "")));
        heads.add(
                Arrays.asList(
                        "消费者进线投诉率最终得分",
                        "消费者进线投诉率权重占" + indexWeightMap.getOrDefault("消费者进线投诉率", "")));
        heads.add(
                Arrays.asList(
                        "虚假报备订单占比", "虚假报备订单占比权重占" + indexWeightMap.getOrDefault("虚假报备订单占比", "")));
        heads.add(
                Arrays.asList(
                        "虚假报备订单占比最终得分",
                        "虚假报备订单占比权重占" + indexWeightMap.getOrDefault("虚假报备订单占比", "")));
        heads.add(Arrays.asList("违规操作率", "违规操作率权重占" + indexWeightMap.getOrDefault("违规操作率", "")));
        heads.add(
                Arrays.asList("违规操作率最终得分", "违规操作率权重占" + indexWeightMap.getOrDefault("违规操作率", "")));
        heads.add(
                Arrays.asList(
                        "骑手进线投诉率", "骑手进线投诉率权重占" + indexWeightMap.getOrDefault("骑手进线投诉率", "")));
        heads.add(
                Arrays.asList(
                        "骑手进线投诉率最终得分", "骑手进线投诉率权重占" + indexWeightMap.getOrDefault("骑手进线投诉率", "")));
        heads.add(Arrays.asList("晨会召开率", "晨会召开率权重占" + indexWeightMap.getOrDefault("晨会召开率", "")));
        heads.add(
                Arrays.asList("晨会召开率最终得分", "晨会召开率权重占" + indexWeightMap.getOrDefault("晨会召开率", "")));
        heads.add(Arrays.asList("QC装备抽检合格率", "倒扣分"));
        heads.add(Arrays.asList("QC装备抽检合格率最终得分", "倒扣分"));
        heads.add(Arrays.asList("业务考试", "业务考试权重占" + indexWeightMap.getOrDefault("业务考试", "")));
        heads.add(Arrays.asList("业务考试最终得分", "业务考试权重占" + indexWeightMap.getOrDefault("业务考试", "")));
        heads.add(
                Arrays.asList(
                        "站长参训合格率", "站长参训合格率权重占" + indexWeightMap.getOrDefault("站长参训合格率", "")));
        heads.add(
                Arrays.asList(
                        "站长参训合格率最终得分", "站长参训合格率权重占" + indexWeightMap.getOrDefault("站长参训合格率", "")));
        heads.add(Arrays.asList("骑手安全管理", "倒扣分"));
        heads.add(Arrays.asList("骑手安全管理最终得分", "倒扣分"));
        heads.add(Arrays.asList("高峰运力波动次数", "倒扣分"));
        heads.add(Arrays.asList("高峰运力波动次数最终得分", "倒扣分"));
        return heads;
    }
}
