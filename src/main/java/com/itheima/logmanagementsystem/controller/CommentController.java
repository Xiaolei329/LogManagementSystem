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

    @GetMapping("/list/{articleId}")
    public Result<List<Comment>> getList(@PathVariable Integer articleId) {
        return Result.success(commentService.getCommentsByArticleId(articleId));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Comment comment) {
        commentService.addComment(comment);
        return Result.success("评论成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        // 这里简化处理，实际应从 Token 获取当前用户 ID 判断权限
        commentService.deleteComment(id, null, false);
        return Result.success("删除成功");
    }
}
