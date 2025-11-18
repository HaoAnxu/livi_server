package com.anxu.smarthomeunity;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.anxu.smarthomeunity.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FastJson练习题
 *
 * @Author: haoanxu
 * @Date: 2025/11/17 11:04
 */
public class FastJsonTest {
    public static void main(String[] args) {
//        System.out.println("第一题：");
//        User user = new User("张三", 25);
////        方式一
////        JSONObject json1 = new JSONObject();
////        json1.put("name", user.getName());
////        json1.put("age", user.getAge());
////        方式二
//        String json1 = JSON.toJSONString(user);
//        System.out.println(json1);
//
//        System.out.println("第二题：");
//        String jsonStr = "{\"name\":\"李四\",\"age\":30,\"address\":\"北京\"}";
//        JSONObject josn2 = JSONObject.parseObject(jsonStr);
//        System.out.println(josn2.get("address"));
//
//        System.out.println("第三题：");
//        String jsonArrStr = "[{\"name\":\"王五\",\"age\":28},{\"name\":\"赵六\",\"age\":32}]";
//        JSONArray jsonArray = JSONArray.parseArray(jsonArrStr);
//        for (Object obj : jsonArray) {
//            JSONObject jsonObject = (JSONObject) obj;
//            System.out.println(jsonObject.get("name") + "的年龄是：" + jsonObject.get("age"));
//        }
//
//        System.out.println("第四题：");
//        JSONObject json4 = new JSONObject();
//        json4.put("productName", "商品A");
//        json4.put("price", 3999.99);
//        Map<String, Object> map = JSON.parseObject(json4.toJSONString(), new TypeReference<>() {});
//        System.out.println(map.get("price"));

//        System.out.println("第五题：");
//        List<Map<String, Object>> confList = new ArrayList<>();
//        Map<String, Object> map1 = new HashMap<>();
//        map1.put("rpa_key", "V_RWKECO");
//        map1.put("cn_name", "经济器");
//        map1.put("rpa_value", "N");
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("rpa_key", "V_RWKIP");
//        map2.put("cn_name", "防护等级");
//        map2.put("rpa_value", "IP23");
//        confList.add(map1);
//        confList.add(map2);
//        JSONObject json5 = new JSONObject();
//        json5.put("confList", confList);
//        System.out.println(json5);
//
//        System.out.println("第六题：");
//        String jsonStr = "{\"code\":200,\"data\":{\"productName\":\"螺杆机\",\"price\":50000,\"params\":[{\"key\":\"V_RWKDRIVE\",\"value\":\"星三角\"}]}}";
//        JSONObject json6 = JSONObject.parseObject(jsonStr);
//        JSONObject dataJson = json6.getJSONObject("data");
//        JSONArray paramsArray = dataJson.getJSONArray("params");
//        for (Object param : paramsArray) {
//            JSONObject paramObj = (JSONObject) param;
//            System.out.println(paramObj.getString("key") + "：" + paramObj.getString("value"));
//        }
//
//        System.out.println("第七题：");
//        Map<String, Object> orderMap = new HashMap<>();
//        orderMap.put("orderNo", "OD20251115");
//        orderMap.put("amount", 120000);
//        JSONObject json = new JSONObject();
//        json.put("orderInfo", orderMap);
//        json.put("status", "SUCCESS");
//
//        Map<String,Object> map = json.to(new TypeReference<>() {});
//        Map<String,Object> orderInfoMap = (Map<String, Object>) map.get("orderInfo");
//        String orderNo = (String) orderInfoMap.get("orderNo");
//        System.out.println(orderNo);
//
//        System.out.println("第八题：");
//        String jsonStr2 = "{\"name\":\"张三\",\"age\":28,\"address\":null}";
//        Map<String,Object> map3 = JSON.parseObject(jsonStr2, new TypeReference<Map<String, Object>>() {});
//        System.out.println(map3);
//        for (Map.Entry<String, Object> entry : map3.entrySet()) {
//            if (entry.getValue() == null) {
//                map3.put(entry.getKey(),"未知地址");
//            }
//        }
        String orderJson = "{\"orderType\":\"ZST\",\"factory\":\"WXAC\",\"soldToParty\":\"IC-CN20\",\"buyDate\":\"05.11.2025\",\"project\":\"河南空分冷水机组项目\",\"shipDate\":\"03.12.2025\",\"oobId\":16,\"saleGroup\":\"CN04\",\"salesStaff\":\"89380\",\"saleChannel\":10,\"productGroup\":10,\"orderDes\":\"河南空分冷水机组项目\",\"shipToParty\":\"IC-CN20\",\"byOrderNo\":\"4511205230-FZZU2522452FEORNCPF\",\"productList\":[{\"modelDes\":\"YVWE270CA50B22WAX\",\"omsiId\":24,\"quantity\":1,\"isSyn\":true,\"productId\":19,\"modelClass\":\"YVWE\",\"firstDate\":\"2025-12-03\",\"ptpUnitPriceTaxIn\":\"313700\",\"modelSelect\":[{\"selectList\":[{\"cn_name\":\"名义冷量\",\"fof_value\":\"100\",\"value_des\":\"100\",\"rpa_value\":\"100\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"130\",\"value_des\":\"130\",\"rpa_value\":\"130\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"170\",\"value_des\":\"170\",\"rpa_value\":\"170\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"200\",\"value_des\":\"200\",\"rpa_value\":\"200\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"240\",\"value_des\":\"240\",\"rpa_value\":\"240\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"270\",\"value_des\":\"270\",\"rpa_value\":\"270\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"280\",\"value_des\":\"280\",\"rpa_value\":\"280\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"310\",\"value_des\":\"310\",\"rpa_value\":\"310\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"355\",\"value_des\":\"355\",\"rpa_value\":\"355\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"360\",\"value_des\":\"360\",\"rpa_value\":\"360\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"420\",\"value_des\":\"420\",\"rpa_value\":\"420\",\"rpa_key\":\"V_YVWENOMCAP\"},{\"cn_name\":\"名义冷量\",\"fof_value\":\"430\",\"value_des\":\"430\",\"rpa_value\":\"430\",\"rpa_key\":\"V_YVWENOMCAP\"}],\"propertyName\":\"V_YVWENOMCAP\",\"sap_value\":\"270\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"名义冷量\",\"tipFlag\":\"false\",\"sap_des\":\"270\",\"value\":\"270\",\"_X_ROW_KEY\":\"row_251\"},{\"selectList\":[{\"cn_name\":\"运用\",\"fof_value\":\"Air-conditioning\",\"value_des\":\"空调\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWEAPPL\"},{\"cn_name\":\"运用\",\"fof_value\":\"ITS\",\"value_des\":\"蓄冰双工况\",\"rpa_value\":\"D\",\"rpa_key\":\"V_YVWEAPPL\"}],\"propertyName\":\"V_YVWEAPPL\",\"sap_value\":\"C\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"运用\",\"tipFlag\":\"false\",\"sap_des\":\"空调\",\"value\":\"Air-conditioning\",\"_X_ROW_KEY\":\"row_252\"},{\"selectList\":[{\"cn_name\":\"制冷剂\",\"fof_value\":\"A\",\"value_des\":\"HFC-134a\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEREF\"}],\"propertyName\":\"V_YVWEREF\",\"sap_value\":\"A\",\"tip\":\"\",\"isDisabled\":true,\"labelName\":\"制冷剂\",\"tipFlag\":\"false\",\"sap_des\":\"HFC-134a\",\"value\":\"R-134a\",\"_X_ROW_KEY\":\"row_253\"},{\"selectList\":[{\"cn_name\":\"电压\",\"fof_value\":\"380V/50Hz/3Ph\",\"value_des\":\"380/3/50\",\"rpa_value\":\"50\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"400V/50Hz/3Ph\",\"value_des\":\"400/3/50\",\"rpa_value\":\"53\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"415V/50Hz/3Ph\",\"value_des\":\"415/3/50\",\"rpa_value\":\"55\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"380V/60Hz/3Ph\",\"value_des\":\"380/3/60\",\"rpa_value\":\"60\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"400V/60Hz/3Ph\",\"value_des\":\"400/3/60\",\"rpa_value\":\"63\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"415V/60Hz/3Ph\",\"value_des\":\"415/3/60\",\"rpa_value\":\"65\",\"rpa_key\":\"V_YVWEVOLTS\"},{\"cn_name\":\"电压\",\"fof_value\":\"460V/60Hz/3Ph\",\"value_des\":\"66\",\"rpa_value\":\"66\",\"rpa_key\":\"V_YVWEVOLTS\"}],\"propertyName\":\"V_YVWEVOLTS\",\"sap_value\":\"50\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"电压\",\"tipFlag\":\"false\",\"sap_des\":\"380/3/50\",\"value\":\"380V/50Hz/3Ph\",\"_X_ROW_KEY\":\"row_254\"},{\"selectList\":[{\"cn_name\":\"设计系列号\",\"fof_value\":\"A\",\"value_des\":\"设计系列A\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEDESIGNLVL\"},{\"cn_name\":\"设计系列号\",\"fof_value\":\"B\",\"value_des\":\"设计系列B\",\"rpa_value\":\"B\",\"rpa_key\":\"V_YVWEDESIGNLVL\"}],\"propertyName\":\"V_YVWEDESIGNLVL\",\"sap_value\":\"B\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"设计系列号\",\"tipFlag\":\"false\",\"sap_des\":\"设计系列B\",\"value\":\"B\",\"_X_ROW_KEY\":\"row_255\"},{\"selectList\":[{\"cn_name\":\"压缩机种类\",\"fof_value\":\"N\",\"value_des\":\"定VI空调压缩机\",\"rpa_value\":\"W\",\"rpa_key\":\"V_YVWECPRVAR\"},{\"cn_name\":\"压缩机种类\",\"fof_value\":\"Y\",\"value_des\":\"可变VI空调压缩机\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWECPRVAR\"}],\"propertyName\":\"V_YVWECPRVAR\",\"sap_value\":\"W\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"压缩机种类\",\"tipFlag\":\"false\",\"sap_des\":\"定VI空调压缩机\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_256\"},{\"selectList\":[{\"cn_name\":\"容器规范\",\"fof_value\":\"China PV Code\",\"value_des\":\"GB压力容器规范\",\"rpa_value\":\"G\",\"rpa_key\":\"V_YVWEVESSEL\"},{\"cn_name\":\"容器规范\",\"fof_value\":\"ASME Code\",\"value_des\":\"ASME压力容器规范\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEVESSEL\"},{\"cn_name\":\"容器规范\",\"fof_value\":\"目前FOF中无对应字段，需结合SQ\",\"value_des\":\"DOSH压力容器规范\",\"rpa_value\":\"D\",\"rpa_key\":\"V_YVWEVESSEL\"}],\"propertyName\":\"V_YVWEVESSEL\",\"sap_value\":\"G\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"容器规范\",\"tipFlag\":\"false\",\"sap_des\":\"GB压力容器规范\",\"value\":\"China PV Code\",\"_X_ROW_KEY\":\"row_257\"},{\"selectList\":[{\"cn_name\":\"蒸冷水侧连接形式\",\"fof_value\":\"Welded Flange (HG/T 20615)\",\"value_des\":\"HG焊接法兰\",\"rpa_value\":\"H\",\"rpa_key\":\"V_YVWEWBCON\"},{\"cn_name\":\"蒸冷水侧连接形式\",\"fof_value\":\"Victaulic Groove\",\"value_des\":\"卡箍\",\"rpa_value\":\"G\",\"rpa_key\":\"V_YVWEWBCON\"}],\"propertyName\":\"V_YVWEWBCON\",\"sap_value\":\"H\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸冷水侧连接形式\",\"tipFlag\":\"false\",\"sap_des\":\"HG焊接法兰\",\"value\":\"Welded Flange (HG/T 20615)\",\"_X_ROW_KEY\":\"row_258\"},{\"selectList\":[{\"cn_name\":\"蒸发器流程\",\"fof_value\":\"1\",\"value_des\":\"1 流程\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWEEVAPPASS\"},{\"cn_name\":\"蒸发器流程\",\"fof_value\":\"2\",\"value_des\":\"2 流程\",\"rpa_value\":\"2\",\"rpa_key\":\"V_YVWEEVAPPASS\"},{\"cn_name\":\"蒸发器流程\",\"fof_value\":\"3\",\"value_des\":\"3 流程\",\"rpa_value\":\"3\",\"rpa_key\":\"V_YVWEEVAPPASS\"}],\"propertyName\":\"V_YVWEEVAPPASS\",\"sap_value\":\"2\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸发器流程\",\"tipFlag\":\"false\",\"sap_des\":\"2 流程\",\"value\":\"2\",\"_X_ROW_KEY\":\"row_259\"},{\"selectList\":[{\"cn_name\":\"蒸发器水侧设计压力\",\"fof_value\":\"150 PSIG DWP\",\"value_des\":\"水侧150磅\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWEEVAPDWP\"},{\"cn_name\":\"蒸发器水侧设计压力\",\"fof_value\":\"300 PSIG DWP\",\"value_des\":\"水侧300磅\",\"rpa_value\":\"3\",\"rpa_key\":\"V_YVWEEVAPDWP\"}],\"propertyName\":\"V_YVWEEVAPDWP\",\"sap_value\":\"1\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸发器水侧设计压力\",\"tipFlag\":\"false\",\"sap_des\":\"水侧150磅\",\"value\":\"150 PSIG DWP\",\"_X_ROW_KEY\":\"row_260\"},{\"selectList\":[{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"AH\",\"value_des\":\"AH\",\"rpa_value\":\"A\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"HA\",\"value_des\":\"HA\",\"rpa_value\":\"H\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"CB\",\"value_des\":\"CB\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"KJ\",\"value_des\":\"KJ\",\"rpa_value\":\"K\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"GN\",\"value_des\":\"GN\",\"rpa_value\":\"G\",\"rpa_key\":\"V_YVWEEVAPARR\"},{\"cn_name\":\"蒸发器水箱布置\",\"fof_value\":\"PF\",\"value_des\":\"PF\",\"rpa_value\":\"P\",\"rpa_key\":\"V_YVWEEVAPARR\"}],\"propertyName\":\"V_YVWEEVAPARR\",\"sap_value\":\"C\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"蒸发器水箱布置\",\"tipFlag\":\"false\",\"sap_des\":\"CB\",\"value\":\"CB\",\"_X_ROW_KEY\":\"row_261\"},{\"selectList\":[{\"cn_name\":\"冷凝器流程\",\"fof_value\":\"1\",\"value_des\":\"1 流程\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWECONDPASS\"},{\"cn_name\":\"冷凝器流程\",\"fof_value\":\"2\",\"value_des\":\"2 流程\",\"rpa_value\":\"2\",\"rpa_key\":\"V_YVWECONDPASS\"}],\"propertyName\":\"V_YVWECONDPASS\",\"sap_value\":\"2\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器流程\",\"tipFlag\":\"false\",\"sap_des\":\"2 流程\",\"value\":\"2\",\"_X_ROW_KEY\":\"row_262\"},{\"selectList\":[{\"cn_name\":\"冷凝器水箱设计压力\",\"fof_value\":\"150 PSIG DWP\",\"value_des\":\"水侧150磅\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWECONDDWP\"},{\"cn_name\":\"冷凝器水箱设计压力\",\"fof_value\":\"300 PSIG DWP\",\"value_des\":\"水侧300磅\",\"rpa_value\":\"3\",\"rpa_key\":\"V_YVWECONDDWP\"}],\"propertyName\":\"V_YVWECONDDWP\",\"sap_value\":\"1\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器水箱设计压力\",\"tipFlag\":\"false\",\"sap_des\":\"水侧150磅\",\"value\":\"150 PSIG DWP\",\"_X_ROW_KEY\":\"row_263\"},{\"selectList\":[{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"PQ\",\"value_des\":\"PQ\",\"rpa_value\":\"P\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"QP\",\"value_des\":\"QP\",\"rpa_value\":\"Q\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"RS\",\"value_des\":\"RS\",\"rpa_value\":\"R\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"TU\",\"value_des\":\"TU\",\"rpa_value\":\"T\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"VW\",\"value_des\":\"VW\",\"rpa_value\":\"V\",\"rpa_key\":\"V_YVWECONDARR\"},{\"cn_name\":\"冷凝器水箱布置\",\"fof_value\":\"XY\",\"value_des\":\"XY\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWECONDARR\"}],\"propertyName\":\"V_YVWECONDARR\",\"sap_value\":\"R\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器水箱布置\",\"tipFlag\":\"false\",\"sap_des\":\"RS\",\"value\":\"RS\",\"_X_ROW_KEY\":\"row_264\"},{\"selectList\":[{\"cn_name\":\"冷凝器水箱形式\",\"fof_value\":\"N\",\"value_des\":\"紧凑式水箱\",\"rpa_value\":\"CA\",\"rpa_key\":\"V_YVWECWBTYPE\"},{\"cn_name\":\"冷凝器水箱形式\",\"fof_value\":\"150 PSIG DWP\",\"value_des\":\"小球清洗水箱\",\"rpa_value\":\"BA(150 PSIG DWP)\",\"rpa_key\":\"V_YVWECWBTYPE\"},{\"cn_name\":\"冷凝器水箱形式\",\"fof_value\":\"300 PSIG DWP\",\"value_des\":\"小球清洗水箱\",\"rpa_value\":\"BA(300 PSIG DWP)\",\"rpa_key\":\"V_YVWECWBTYPE\"}],\"propertyName\":\"V_YVWECWBTYPE\",\"sap_value\":\"CA\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"冷凝器水箱形式\",\"tipFlag\":\"false\",\"sap_des\":\"紧凑式水箱\",\"value\":\"紧凑式水箱\",\"_X_ROW_KEY\":\"row_265\"},{\"selectList\":[{\"cn_name\":\"截止阀选项\",\"fof_value\":\"N\",\"value_des\":\"不带隔离阀\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEVALVE\"},{\"cn_name\":\"截止阀选项\",\"fof_value\":\"Y\",\"value_des\":\"冷凝器隔离阀\",\"rpa_value\":\"B\",\"rpa_key\":\"V_YVWEVALVE\"}],\"propertyName\":\"V_YVWEVALVE\",\"sap_value\":\"X\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"截止阀选项\",\"tipFlag\":\"false\",\"sap_des\":\"不带隔离阀\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_266\"},{\"selectList\":[{\"cn_name\":\"保温\",\"fof_value\":\"3/4\\\" (19 mm)\",\"value_des\":\"蒸发器19mm保温\",\"rpa_value\":\"S\",\"rpa_key\":\"V_YVWEINS\"},{\"cn_name\":\"保温\",\"fof_value\":\"1-1/2\\\" (38mm)\",\"value_des\":\"蒸发器38mm保温\",\"rpa_value\":\"D\",\"rpa_key\":\"V_YVWEINS\"},{\"cn_name\":\"保温\",\"fof_value\":\"1\\\" (25mm)\",\"value_des\":\"蒸发器25mm保温\",\"rpa_value\":\"F\",\"rpa_key\":\"V_YVWEINS\"},{\"cn_name\":\"保温\",\"fof_value\":\"Not Required\",\"value_des\":\"没有保温\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEINS\"}],\"propertyName\":\"V_YVWEINS\",\"sap_value\":\"S\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"保温\",\"tipFlag\":\"false\",\"sap_des\":\"蒸发器19mm保温\",\"value\":\"3/4\\\" (19 mm)\",\"_X_ROW_KEY\":\"row_267\"},{\"selectList\":[{\"cn_name\":\"隔音组件\",\"fof_value\":\"N\",\"value_des\":\"不带隔音罩（无隔音套件）\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWENOISERDCT\"},{\"cn_name\":\"隔音组件\",\"fof_value\":\"Y\",\"value_des\":\"部分区域包隔音罩(隔音套件1)\",\"rpa_value\":\"B\",\"rpa_key\":\"V_YVWENOISERDCT\"},{\"cn_name\":\"隔音组件\",\"fof_value\":\"？？？\",\"value_des\":\"整机包隔音罩(隔音套件2)\",\"rpa_value\":\"C\",\"rpa_key\":\"V_YVWENOISERDCT\"}],\"propertyName\":\"V_YVWENOISERDCT\",\"sap_value\":\"X\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"隔音组件\",\"tipFlag\":\"false\",\"sap_des\":\"不带隔音罩（无隔音套件）\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_268\"},{\"propertyName\":\"V_YVWEDEST\",\"sap_value\":\"C\",\"tip\":\"\",\"isDisabled\":true,\"labelName\":\"发运目的地\",\"tipFlag\":\"false\",\"sap_des\":\"中国大陆\",\"value\":\"中国大陆\",\"_X_ROW_KEY\":\"row_269\"},{\"selectList\":[{\"cn_name\":\"发运方式\",\"fof_value\":\"Form1: Refrigerant shipped in unit\",\"value_des\":\"发运方式1\",\"rpa_value\":\"1\",\"rpa_key\":\"V_YVWEFORM\"},{\"cn_name\":\"发运方式\",\"fof_value\":\"Form2: Refrigerant not included\",\"value_des\":\"发运方式2\",\"rpa_value\":\"2\",\"rpa_key\":\"V_YVWEFORM\"}],\"propertyName\":\"V_YVWEFORM\",\"sap_value\":\"1\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"发运方式\",\"tipFlag\":\"false\",\"sap_des\":\"发运方式1\",\"value\":\"Form1: Refrigerant shipped in unit\",\"_X_ROW_KEY\":\"row_270\"},{\"selectList\":[{\"cn_name\":\"滤波器\",\"fof_value\":\"N\",\"value_des\":\"不带滤波器\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEFILTER\"},{\"cn_name\":\"滤波器\",\"fof_value\":\"Y\",\"value_des\":\"带滤波器\",\"rpa_value\":\"F\",\"rpa_key\":\"V_YVWEFILTER\"}],\"propertyName\":\"V_YVWEFILTER\",\"sap_value\":\"X\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"滤波器\",\"tipFlag\":\"false\",\"sap_des\":\"不带滤波器\",\"value\":\"N\",\"_X_ROW_KEY\":\"row_271\"},{\"selectList\":[{\"cn_name\":\"楼宇自动化系统\",\"fof_value\":\"Modbus\",\"value_des\":\"Modbus\",\"rpa_value\":\"X\",\"rpa_key\":\"V_YVWEBAS\"},{\"cn_name\":\"楼宇自动化系统\",\"fof_value\":\"SC-Equipment – BACnet/N2\",\"value_des\":\"SC-Equip Board\",\"rpa_value\":\"S\",\"rpa_key\":\"V_YVWEBAS\"},{\"cn_name\":\"楼宇自动化系统\",\"fof_value\":\"LON E-link\",\"value_des\":\"LON E-link\",\"rpa_value\":\"L\",\"rpa_key\":\"V_YVWEBAS\"}],\"propertyName\":\"V_YVWEBAS\",\"sap_value\":\"S\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"楼宇自动化系统\",\"tipFlag\":\"false\",\"sap_des\":\"SC-Equip Board\",\"value\":\"SC-Equipment – BACnet/N2\",\"_X_ROW_KEY\":\"row_272\"},{\"propertyName\":\"V_YVWETMR\",\"sap_value\":\"\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"TMR智能设备支持套件\",\"tipFlag\":\"false\",\"sap_des\":\"\",\"value\":\"\",\"_X_ROW_KEY\":\"row_273\"},{\"propertyName\":\"V_YVWESCAP\",\"sap_value\":\"\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"智能设备支持套件\",\"tipFlag\":\"false\",\"sap_des\":\"\",\"value\":\"\",\"_X_ROW_KEY\":\"row_274\"},{\"propertyName\":\"V_YVWEDSTN\",\"sap_value\":\"\",\"tip\":\"\",\"isDisabled\":false,\"labelName\":\"目的地国家\",\"tipFlag\":\"false\",\"sap_des\":\"\",\"value\":\"\",\"_X_ROW_KEY\":\"row_275\"}],\"ptpUnitPriceTaxEx\":\"277610.84\",\"_X_ROW_KEY\":\"row_241\",\"btp\":\"313700\"}],\"saleOffice\":\"CN35\"}";
        JSONObject jsonObject = JSONObject.parseObject(orderJson);
        System.out.println(jsonObject.getString("project"));
        System.out.println(jsonObject.getInteger("oobId"));
        System.out.println(jsonObject.getString("factory"));
        System.out.println(jsonObject.getString("byOrderNo"));
        String saleGroup = jsonObject.getString("saleGroup");
        if (!jsonObject.containsKey("saleGroup") || saleGroup == null || "".equals(saleGroup)) {
            jsonObject.put("saleGroup", "默认销售组");
        }
        JSONArray productList = jsonObject.getJSONArray("productList");
        for (Object o : productList) {
            JSONObject product = (JSONObject) o;
            String modelDes = product.getString("modelDes");
            Integer productId = product.getInteger("productId");
            Integer quantity = product.getInteger("quantity");
            String ptpUnitPriceTaxInStr = product.getString("ptpUnitPriceTaxIn");
            double ptpUnitPriceTaxIn = 0.0;
            if (ptpUnitPriceTaxInStr != null && !"".equals(ptpUnitPriceTaxInStr)) {
                ptpUnitPriceTaxIn = Double.parseDouble(ptpUnitPriceTaxInStr);
            }
        }
        JSONArray modelSelect = productList.getJSONObject(0).getJSONArray("modelSelect");
        for (Object o : modelSelect) {
            JSONObject model = (JSONObject) o;
            if(model.getString("propertyName").equals("V_YVWENOMCAP")){
                JSONArray selectList = model.getJSONArray("selectList");
                for (Object select : selectList) {
                    JSONObject selectObj = (JSONObject) select;
                    String fofValue = selectObj.getString("fof_value");
                    String valueDes = selectObj.getString("value_des");
                    System.out.println(fofValue + "：" + valueDes);

                }
            }
        }
    }
}
