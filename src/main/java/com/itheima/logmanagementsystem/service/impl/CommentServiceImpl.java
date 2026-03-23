package com.itheima.logmanagementsystem.service.impl;

import com.itheima.logmanagementsystem.entity.Comment;
import com.itheima.logmanagementsystem.mapper.CommentMapper;
import com.itheima.logmanagementsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByArticleId(Integer articleId) {
        return commentMapper.findByArticleId(articleId);
    }

    @Override
    @Transactional
    public void addComment(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentId, Integer userId, boolean isAdmin) {
        commentMapper.deleteById(commentId);
    }
}
