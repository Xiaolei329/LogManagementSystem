package com.itheima.logmanagementsystem.service;

import com.itheima.logmanagementsystem.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    void register(User user);
    User getUserById(Integer userId);
    void updateUser(User user);
    List<User> getAllUsers();
    void deleteUser(Integer userId);
    void auditUser(Integer userId, Integer status);
}
