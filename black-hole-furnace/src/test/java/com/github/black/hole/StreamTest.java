package com.github.black.hole;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.mvel2.MVEL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * @author hairen.long
 * @date 2020/11/3
 */
public class StreamTest {

    public static void main(String[] args) {
        String str =
                "[{\"id\":2,\"ruleName\":\"上级继承下级人地关系\",\"ruleDesc\":\"1.该组织下的所有人的上级是该组织的“组织负责人”；2.该组织下的所有人的上级，继承所有人的“人-地”关系\",\"applyScene\":\"物流战团负责人继承配送经理的数据权限\",\"applyType\":3},{\"id\":10,\"ruleName\":\"按商圈所在城市继承\",\"ruleDesc\":\"1.确定该角色下的所有人拥有的人—商圈关系；2.将角色下所有人所管辖商圈对应的城市找出；3.将根据该角色下所有人找出的人—城市关系，写入资源树”；2.该组织下的所有人的上级，继承所有人的“人-地”关系\",\"applyScene\":\"全场景\",\"applyType\":3},{\"id\":18,\"ruleName\":\"按站点所在城市继承\",\"ruleDesc\":\"1.确定该角色下的所有人拥有的人—站点关系；2.将角色下所有人所管辖站点对应的城市找出；3.将根据该角色下所有人找出的人—城市关系，写入资源树”；2.该组织下的所有人的上级，继承所有人的“人-地”关系\",\"applyScene\":\"全场景\",\"applyType\":3},{\"id\":26,\"ruleName\":\"按城市管辖商圈继承\",\"ruleDesc\":\"1.确定该角色下的所有人拥有的人—城市关系；2.将角色下所有人所管辖城市对应的商圈找出；3.将根据该角色下所有人找出的人—商圈关系，写入资源树”；2.该组织下的所有人的上级，继承所有人的“人-地”关系\",\"applyScene\":\"全场景\",\"applyType\":3},{\"id\":34,\"ruleName\":\"按城市管辖站点继承\",\"ruleDesc\":\"1.确定该角色下的所有人拥有的人—城市关系；2.将角色下所有人所管辖城市对应的站点找出；3.将根据该角色下所有人找出的人—站点关系，写入资源树”；2.该组织下的所有人的上级，继承所有人的“人-地”关系\",\"applyScene\":\"全场景\",\"applyType\":3},{\"id\":42,\"ruleName\":\"按战区管辖城市继承\",\"ruleDesc\":\"1.确定该角色下的所有人拥有的人—战区关系；2.将角色下所有人所管辖的战区对应的城市找出；3.将根据该角色下所有人找出的人—城市关系，写入资源树”；2.该组织下的所有人的上级，继承所有人的“人-地”关系\",\"applyScene\":\"全场景\",\"applyType\":3},{\"id\":50,\"ruleName\":\"入离异自动化\",\"ruleDesc\":\"1.确定该角色下所有人&关联EHR的规则；2.确定该角色确定在资源树上挂在了固定节点“全国”；3.同时满足1&2 所有人的需要感知他们的入，离异；4.该角色规则关联EHR的人，入职后第二天0:00绑定固定节点“全国”；5.该角色下所有人，异动和离职后，自动解绑跟固定节点“全国”的关系”；2.该组织下的所有人的上级，继承所有人的“人-地”关系\",\"applyScene\":\"全场景\",\"applyType\":3}]";
        List<DeliveryResourceBasicRuleDO> rules =
                JSON.parseArray(str, DeliveryResourceBasicRuleDO.class);
        System.out.println(rules);
    }

    public static class DeliveryResourceBasicRuleDO {
        /** 主键ID */
        private Long id;
        /** 规则名称 */
        private String ruleName;
        /** 规则描述 */
        private String ruleDesc;
        /** 适用场景 */
        private String applyScene;
        /** 适用类型 1:组织,2:角色 */
        private Integer applyType;
        /** 软删,1:有效,0:删除 */
        private Integer isActive;
        /** 创建时间 */
        private Timestamp createdAt;
        /** 更新时间 */
        private Timestamp updatedAt;
    }

    public void test() {
        String expression = "((y2-y1)/(x2-x1))*(x3-x2)+y2";
        Map<String, BigDecimal> data = Maps.newHashMap();
        data.put("y2", BigDecimal.valueOf(100));
        data.put("y1", BigDecimal.valueOf(0));
        data.put("x2", BigDecimal.valueOf(1));
        data.put("x1", BigDecimal.valueOf(0.5));
        data.put("x3", BigDecimal.valueOf(0.935048285));
        try {
            Object y3 = MVEL.eval(expression, data);
            BigDecimal result = new BigDecimal(y3.toString());
            System.out.println(result.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
