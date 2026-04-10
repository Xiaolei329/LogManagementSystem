package com.itheima.logmanagementsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.logmanagementsystem.entity.LogArticle;
import com.itheima.logmanagementsystem.mapper.CommentMapper;
import com.itheima.logmanagementsystem.mapper.LogArticleMapper;
import com.itheima.logmanagementsystem.service.LogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogArticleServiceImpl implements LogArticleService {

    @Autowired
    private LogArticleMapper logArticleMapper;
    
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private com.itheima.logmanagementsystem.service.AIAssistantService aiAssistantService;

    @Autowired
    private org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate;

    @Override
    public PageInfo<LogArticle> getArticleList(int pageNum, int pageSize, String title) {
        PageHelper.startPage(pageNum, pageSize);
        List<LogArticle> list = logArticleMapper.findAll(title);
        return new PageInfo<>(list);
    }

    @Override
    public LogArticle getArticleDetail(Integer articleId) {
        logArticleMapper.incrementReadCount(articleId);
        
        //热度排行榜
        String key = "article:hot";
        stringRedisTemplate.opsForZSet().incrementScore(key, String.valueOf(articleId), 1);
        
        return logArticleMapper.findById(articleId);
    }

    @Override
    @Transactional
    public void publishArticle(LogArticle article) {
        logArticleMapper.insert(article);
        if (article.getArticleId() != null) {
            aiAssistantService.generateAndAddComment(article);
        }
    }

    @Override
    @Transactional
    public void updateArticle(LogArticle article) {
        logArticleMapper.update(article);
    }

    @Override
    @Transactional
    public void deleteArticle(Integer articleId) {
        // 删除日志前先删除评论
        commentMapper.deleteByArticleId(articleId);
        logArticleMapper.deleteById(articleId);
    }

    @Override
    public PageInfo<LogArticle> getMyArticles(int pageNum, int pageSize, Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        List<LogArticle> list = logArticleMapper.findByUserId(userId);
        return new PageInfo<>(list);
    }
}
