package com.itheima.logmanagementsystem.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itheima.logmanagementsystem.entity.Comment;
import com.itheima.logmanagementsystem.entity.LogArticle;
import com.itheima.logmanagementsystem.mapper.CommentMapper;
import com.itheima.logmanagementsystem.service.AIAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class AIAssistantServiceImpl implements AIAssistantService {

    @Value("${ai.api-key}")
    private String apiKey;

    @Value("${ai.api-url}")
    private String apiUrl;

    @Value("${ai.bot-user-id}")
    private Integer botUserId;

    @Autowired
    private CommentMapper commentMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Override
    @Async
    public void generateAndAddComment(LogArticle article) {
        String systemPrompt = "你是公司的日志管理系统小助手。某位员工刚刚发布了一篇工作日志。" +
                "请你用热情、鼓励的语气，对他/她的工作日志给出一句简短的专业点评或关怀互动（字数严苛控制在50字内，不需要打招呼，直接根据内容回复即可）。";
        String userContent = "标题：" + article.getTitle() + "\n内容：" + article.getContent();

        try {
            String aiResponse = callFastLLM(systemPrompt, userContent);
            if (aiResponse != null && !aiResponse.trim().isEmpty()) {
                // 构建评论
                Comment comment = new Comment();
                comment.setArticleId(article.getArticleId());
                comment.setUserId(botUserId);
                comment.setContent(aiResponse.trim());
                comment.setParentId(0);
                commentMapper.insert(comment);
                System.out.println("AI 评论已成功: " + aiResponse);
                
                // WebSocket 通知作者新评论
                if (article.getUserId() != null) {
                    com.itheima.logmanagementsystem.websocket.WebSocketServer.sendMessageToUser(
                            String.valueOf(article.getUserId()),
                            "新评论提醒：系统小助手刚刚点评了您的日志！"
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("AI 评论失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String polishContent(String rawContent) {
        String systemPrompt = "你是一个专业的文案润色助手和资深工程师。请帮我润色以下工作日志内容，" +
                "使其看起来排版更清晰、逻辑更严密、语言更显专业，修复错别字。不要改变原意，直接输出润色后的内容即可，" +
                "不需要包含诸如'好的，以下是润色效果'等废话。如果内容很短也不要过度发散。";
        try {
            String result = callFastLLM(systemPrompt, rawContent);
            if (result != null && !result.trim().isEmpty()) {
                return result.trim();
            }
            throw new RuntimeException("大模型返回内容为空");
        } catch (Exception e) {
            throw new RuntimeException("大模型调用出错", e);
        }
    }

    /**
     * 通用的调用大模型接口
     */
    private String callFastLLM(String systemPrompt, String userMessage) throws Exception {
        ObjectNode requestBody = objectMapper.createObjectNode();

        requestBody.put("model", "deepseek-chat"); 
        
        ArrayNode messages = requestBody.putArray("messages");
        
        ObjectNode msgSys = messages.addObject();
        msgSys.put("role", "system");
        msgSys.put("content", systemPrompt);

        ObjectNode msgUsr = messages.addObject();
        msgUsr.put("role", "user");
        msgUsr.put("content", userMessage);

        requestBody.put("stream", false);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        // 超时时间
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("API 调用失败，状态码: " + response.statusCode() + " 详细: " + response.body());
        }

        JsonNode root = objectMapper.readTree(response.body());
        JsonNode choices = root.path("choices");
        if (choices.isArray() && choices.size() > 0) {
            JsonNode message = choices.get(0).path("message");
            return message.path("content").asText();
        }

        return null;
    }
}
