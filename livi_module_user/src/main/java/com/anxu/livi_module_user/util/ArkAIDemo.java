package com.anxu.livi_module_user.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.ark.runtime.model.responses.item.ItemEasyMessage;
import com.volcengine.ark.runtime.model.responses.item.MessageContent;
import com.volcengine.ark.runtime.model.responses.request.CreateResponsesRequest;
import com.volcengine.ark.runtime.model.responses.response.ResponseObject;
import com.volcengine.ark.runtime.model.responses.constant.ResponsesConstants;
import com.volcengine.ark.runtime.model.responses.content.InputContentItemText;
import com.volcengine.ark.runtime.service.ArkService;
import com.volcengine.ark.runtime.model.responses.item.*;
import com.volcengine.ark.runtime.model.responses.request.*;
/**
 * 简化版火山方舟AI调用工具类
 * 为AI设定练气期修士人设
 */
public class ArkAIDemo {
    // JSON序列化工具（按需使用）
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        // 1. 配置基础信息
        String apiKey = "17583982-6e62-43c1-a466-c65e46319041";
        String modelId = "ep-20260112141510-9xs9k";
        String baseUrl = "https://ark.cn-beijing.volces.com/api/v3";

        // 2. 创建ArkService实例
        ArkService arkService = ArkService.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();

        try {
            // 3. 构建请求 - 核心：添加练气期修士人设
            // 人设提示词：明确AI的身份、语气、说话方式
            String aiPersona = "你现在是一位刚踏入修行之路的练气期修士，说话要符合修士的身份，" +
                    "常用修仙术语，是一位欺软怕硬的小妹，回答问题要结合修仙世界观。";
            // 用户实际问题（可以替换成你想提问的内容）
            String userQuestion = "道友你好啊，请问你现在什么修为了？";
            // 组合人设和问题
            String finalPrompt = aiPersona + "\n\n" + userQuestion;

            CreateResponsesRequest request = CreateResponsesRequest.builder()
                    .model(modelId)
                    .input(ResponsesInput.builder()
                            .addListItem(ItemEasyMessage.builder()
                                    .role(ResponsesConstants.MESSAGE_ROLE_USER)
                                    .content(MessageContent.builder()
                                            .addListItem(InputContentItemText.builder()
                                                    .text(finalPrompt) // 传入带人设的完整提示词
                                                    .build())
                                            .build())
                                    .build())
                            .build())
                    .build();

            // 4. 调用AI接口并获取响应
            ResponseObject response = arkService.createResponse(request);
            System.out.println("AI响应结果：");
            System.out.println(response);

        } catch (Exception e) {
            // 异常处理：打印错误信息，方便调试
            System.err.println("调用AI接口失败：" + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. 关闭执行器，释放资源
            arkService.shutdownExecutor();
        }
    }
}