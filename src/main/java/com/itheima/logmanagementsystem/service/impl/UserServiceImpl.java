package com.itheima.logmanagementsystem.service.impl;

import com.itheima.logmanagementsystem.entity.User;
import com.itheima.logmanagementsystem.mapper.UserMapper;
import com.itheima.logmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.login(username, password);
        if (user != null && user.getStatus() != 1) {
            throw new RuntimeException("账号未审核或已禁用");
        }
        return user;
    }

    @Override
    @Transactional
    public void register(User user) {
        User exist = userMapper.findByUsername(user.getUsername());
        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setStatus(0);  // 默认未审核
        user.setIsAdmin(0);  // 默认普通用户
        userMapper.insert(user);
    }

    @Override
    public User getUserById(Integer userId) {
        return userMapper.findById(userId);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        userMapper.deleteById(userId);
    }

    @Override
    @Transactional
    public void auditUser(Integer userId, Integer status) {
        User user = new User();
        user.setUserId(userId);
        user.setStatus(status);
        userMapper.update(user);
    }
}
