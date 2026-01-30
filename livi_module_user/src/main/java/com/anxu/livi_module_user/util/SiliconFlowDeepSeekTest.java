package com.anxu.livi_module_user.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SiliconFlowDeepSeekTest {
    // API Key
    private static final String API_KEY = "sk-ftvbzjmjlhhoxidofjlwghfukbxsarxpjqbzinyhdmsbjdie";
    // SiliconFlow 接口地址
    private static final String API_URL = "https://api.siliconflow.cn/v1/chat/completions";
    // 目标模型名称S
    private static final String MODEL_NAME = "deepseek-ai/DeepSeek-R1-0528-Qwen3-8B";

    public static void main(String[] args) {
        // 1. 配置 OkHttpClient，加长超时时间
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)  // 连接超时
                .readTimeout(60, TimeUnit.SECONDS)     // 读取超时
                .writeTimeout(60, TimeUnit.SECONDS)    // 写入超时
                .build();

        // 2. 构造兼容的请求体（移除不支持的 tools 参数，精简必要参数）
        JSONObject requestBody = new JSONObject();
        // 核心必选参数
        requestBody.put("model", MODEL_NAME);
        requestBody.put("stream", false); // 关闭流式输出（基础必选）
        requestBody.put("max_tokens", 4096);
        requestBody.put("temperature", 0.7);

        // 可选基础参数（模型支持的通用参数）
        requestBody.put("top_p", 0.7);
        requestBody.put("top_k", 50);
        requestBody.put("frequency_penalty", 0.5);
        requestBody.put("n", 1);

        // 响应格式（保持简单文本格式）
        JSONObject responseFormat = new JSONObject();
        responseFormat.put("type", "text");
        requestBody.put("response_format", responseFormat);

        // 对话消息（简单测试内容）
        JSONArray messages = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "hello，请用一句话介绍自己");
        messages.add(userMessage);
        requestBody.put("messages", messages);

        // 3. 转换为 JSON 字符串
        String jsonBody = JSON.toJSONString(requestBody);

        // 4. 构造 HTTP 请求
        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        // 5. 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("请求失败，状态码：" + response.code());
                String errorMsg = response.body() != null ? response.body().string() : "无错误信息";
                System.err.println("错误详情：" + errorMsg);
                return;
            }

            // 解析响应
            String responseBody = response.body().string();
            JSONObject responseJson = JSON.parseObject(responseBody);
            String formattedJson = JSON.toJSONString(responseJson, true);
            System.out.println("模型响应结果：\n" + formattedJson);

            // 提取核心回答内容
            JSONArray choices = responseJson.getJSONArray("choices");
            if (choices != null && !choices.isEmpty()) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject message = choice.getJSONObject("message");
                String content = message.getString("content");
                System.out.println("\n提取的核心回答：\n" + content);
            }

        } catch (IOException e) {
            System.err.println("请求异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}