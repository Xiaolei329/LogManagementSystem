package com.itheima.logmanagementsystem.mapper;

import com.itheima.logmanagementsystem.entity.LogArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogArticleMapper {
    LogArticle findById(Integer articleId);
    List<LogArticle> findAll(@Param("title") String title); // 支持标题模糊搜索
    List<LogArticle> findByUserId(Integer userId);
    int insert(LogArticle article);
    int update(LogArticle article);
    int deleteById(Integer articleId);
    int incrementReadCount(Integer articleId);
}
