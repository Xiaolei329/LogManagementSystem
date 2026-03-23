package com.itheima.logmanagementsystem.mapper;

import com.itheima.logmanagementsystem.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findByArticleId(Integer articleId);
    int insert(Comment comment);
    int deleteById(Integer commentId);
    int deleteByArticleId(Integer articleId);
}
