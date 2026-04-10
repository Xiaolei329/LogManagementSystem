package com.itheima.logmanagementsystem.controller;

import com.itheima.logmanagementsystem.common.Result;
import com.itheima.logmanagementsystem.entity.Comment;
import com.itheima.logmanagementsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private com.itheima.logmanagementsystem.mapper.LogArticleMapper logArticleMapper;

    @GetMapping("/list/{articleId}")
    public Result<List<Comment>> getList(@PathVariable Integer articleId) {
        return Result.success(commentService.getCommentsByArticleId(articleId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Comment comment) {
        commentService.addComment(comment);
        
        try {
            com.itheima.logmanagementsystem.entity.LogArticle article = logArticleMapper.findById(comment.getArticleId());
            if (article != null && article.getUserId() != null) {
                // 如果不是自己给自己评论，则发送通知
                if (!article.getUserId().equals(comment.getUserId())) {
                    com.itheima.logmanagementsystem.websocket.WebSocketServer.sendMessageToUser(
                            String.valueOf(article.getUserId()),
                            "新评论提醒：有一位同事刚刚评论了您的日志！"
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.success("评论成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        commentService.deleteComment(id, null, false);
        return Result.success("删除成功");
    }
}
