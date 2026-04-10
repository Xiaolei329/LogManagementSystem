package com.itheima.logmanagementsystem.controller;

import com.itheima.logmanagementsystem.common.Result;
import com.itheima.logmanagementsystem.service.AIAssistantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class AIAssistantController {

    @Autowired
    private AIAssistantService aiAssistantService;

    @PostMapping("/polish")
    public Result<String> polish(@RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        if (content == null || content.trim().isEmpty()) {
            return Result.error("内容不能为空");
        }
        
        try {
            String polishedContent = aiAssistantService.polishContent(content);
            return Result.success(polishedContent);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("AI 润色失败: " + e.getMessage());
        }
    }
}
