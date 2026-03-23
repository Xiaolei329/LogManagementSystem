package com.itheima.logmanagementsystem.service;

import com.github.pagehelper.PageInfo;
import com.itheima.logmanagementsystem.entity.LogArticle;

public interface LogArticleService {
    PageInfo<LogArticle> getArticleList(int pageNum, int pageSize, String title);
    LogArticle getArticleDetail(Integer articleId);
    void publishArticle(LogArticle article);
    void updateArticle(LogArticle article);
    void deleteArticle(Integer articleId);
    PageInfo<LogArticle> getMyArticles(int pageNum, int pageSize, Integer userId);
}
