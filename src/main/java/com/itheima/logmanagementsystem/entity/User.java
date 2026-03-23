package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String realName;
    private LocalDate birthday;
    private Integer districtId;
    private String avatarUrl;
    private String phone;
    private String email;
    private String qq;
    private Integer isAdmin; // 0:普通用户 1:管理员
    private Integer status; // 0:未审核 1:正常 2:禁用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
