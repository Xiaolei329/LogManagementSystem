package com.itheima.logmanagementsystem.controller;

import com.itheima.logmanagementsystem.common.Result;
import com.itheima.logmanagementsystem.entity.User;
import com.itheima.logmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        try {
            User loginUser = userService.login(user.getUsername(), user.getPassword());
            if (loginUser != null) {
                return Result.success(loginUser);
            }
            return Result.error("用户名或密码错误");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody User user) {
        try {
            userService.register(user);
            return Result.success("注册成功，请等待审核");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable Integer id) {
        return Result.success(userService.getUserById(id));
    }

    @PutMapping("/update")
    public Result<String> updateInfo(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success("修改成功");
    }

    @GetMapping("/list")
    public Result<List<User>> getUserList() {
        return Result.success(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }

    @PutMapping("/audit/{id}/{status}")
    public Result<String> auditUser(@PathVariable Integer id, @PathVariable Integer status) {
        userService.auditUser(id, status);
        return Result.success("操作成功");
    }
}
