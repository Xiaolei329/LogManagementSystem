package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LogArticle {
    private Integer articleId;
    private String title;
    private String content;
    private Integer userId;
    private Integer readCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String authorName; // 作者姓名
    private String authorAvatar; // 作者头像
}
