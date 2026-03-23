package com.itheima.logmanagementsystem.service;

import com.itheima.logmanagementsystem.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByArticleId(Integer articleId);
    void addComment(Comment comment);
    void deleteComment(Integer commentId, Integer userId, boolean isAdmin);
}
