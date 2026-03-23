package com.itheima.logmanagementsystem.mapper;

import com.itheima.logmanagementsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findById(Integer userId);
    User findByUsername(String username);
    List<User> findAll();
    int insert(User user);
    int update(User user);
    int deleteById(Integer userId);
    
    // 登录验证
    User login(@Param("username") String username, @Param("password") String password);
}
