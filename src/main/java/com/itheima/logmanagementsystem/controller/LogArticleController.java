package com.itheima.logmanagementsystem.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.logmanagementsystem.common.Result;
import com.itheima.logmanagementsystem.entity.LogArticle;
import com.itheima.logmanagementsystem.service.LogArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
@CrossOrigin
public class LogArticleController {

    @Autowired
    private LogArticleService logArticleService;

    @GetMapping("/list")
    public Result<PageInfo<LogArticle>> getList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title) {
        return Result.success(logArticleService.getArticleList(pageNum, pageSize, title));
    }

    @GetMapping("/{id}")
    public Result<LogArticle> getDetail(@PathVariable Integer id) {
        return Result.success(logArticleService.getArticleDetail(id));
    }

    @PostMapping("/publish")
    public Result<String> publish(@RequestBody LogArticle article) {
        logArticleService.publishArticle(article);
        return Result.success("发布成功");
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody LogArticle article) {
        logArticleService.updateArticle(article);
        return Result.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        logArticleService.deleteArticle(id);
        return Result.success("删除成功");
    }

    @GetMapping("/my")
    public Result<PageInfo<LogArticle>> getMyList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam Integer userId) {
        return Result.success(logArticleService.getMyArticles(pageNum, pageSize, userId));
    }
}
