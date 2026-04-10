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

    @Autowired
    private org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate;

    @Autowired
    private com.itheima.logmanagementsystem.mapper.LogArticleMapper logArticleMapper;

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

    @GetMapping("/hot")
    public Result<java.util.List<LogArticle>> getHotArticles() {
        String key = "article:hot";
        java.util.Set<String> topIds = stringRedisTemplate.opsForZSet().reverseRange(key, 0, 9);
        if (topIds == null || topIds.isEmpty()) return Result.success(new java.util.ArrayList<>());
        
        java.util.List<LogArticle> hotList = new java.util.ArrayList<>();
        for (String idStr : topIds) {
            LogArticle article = logArticleMapper.findById(Integer.valueOf(idStr));
            if (article != null) hotList.add(article);
        }
        return Result.success(hotList);
    }
}
