package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Comment {
    private Integer commentId;
    private Integer articleId;
    private Integer userId;
    private String content;
    private Integer parentId;
    private LocalDateTime createTime;

    // 业务扩展字段
    private String userName; // 评论人姓名
    private String userAvatar; // 评论人头像
    private List<Comment> replies; // 回复列表
}
