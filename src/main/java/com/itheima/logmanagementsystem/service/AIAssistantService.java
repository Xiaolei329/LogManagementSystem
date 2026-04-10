package com.itheima.logmanagementsystem.service;

import com.itheima.logmanagementsystem.entity.LogArticle;

public interface AIAssistantService {
    
    /**
     * 异步生成并添加评论
     * @param article 发布成功后的日志对象
     */
    void generateAndAddComment(LogArticle article);

    /**
     * 同步调用大模型润色文本
     * @param rawContent 原始文本
     * @return 润色后的文本
     */
    String polishContent(String rawContent);
}
