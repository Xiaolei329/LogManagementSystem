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

    @Autowired
    private org.springframework.data.redis.core.StringRedisTemplate stringRedisTemplate;

    @GetMapping("/captcha")
    public Result<java.util.Map<String, String>> getCaptcha() {
        // 创建图形验证码
        cn.hutool.captcha.ShearCaptcha captcha = cn.hutool.captcha.CaptchaUtil.createShearCaptcha(150, 40, 4, 3);
        String code = captcha.getCode();
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        
        // 存入 Redis
        stringRedisTemplate.opsForValue().set("login_captcha:" + uuid, code, 2, java.util.concurrent.TimeUnit.MINUTES);
        
        java.util.Map<String, String> result = new java.util.HashMap<>();
        result.put("captchaId", uuid);
        result.put("captchaImg", captcha.getImageBase64Data());
        
        return Result.success(result);
    }

    @PostMapping("/login")
    public Result<java.util.Map<String, Object>> login(@RequestBody java.util.Map<String, String> form) {
        String username = form.get("username");
        String password = form.get("password");
        String captchaCode = form.get("captchaCode");
        String captchaId = form.get("captchaId");

        if (captchaId == null || captchaCode == null || captchaCode.trim().isEmpty()) {
            return Result.error("请输入验证码");
        }
        
        // 校验验证码
        String redisKey = "login_captcha:" + captchaId;
        String realCode = stringRedisTemplate.opsForValue().get(redisKey);
        
        if (realCode == null) {
            return Result.error("验证码已失效，请点击图片刷新");
        }
        if (!realCode.equalsIgnoreCase(captchaCode)) {
            return Result.error("验证码错误");
        }
        
        // 验证通过，删除缓存
        stringRedisTemplate.delete(redisKey);

        try {
            User loginUser = userService.login(username, password);
            if (loginUser != null) {
                // 生成 JWT
                java.util.Map<String, Object> claims = new java.util.HashMap<>();
                claims.put("userId", loginUser.getUserId());
                claims.put("username", loginUser.getUsername());
                String token = com.itheima.logmanagementsystem.utils.JwtUtils.generateToken(claims);

                // 组装返回数据
                java.util.Map<String, Object> data = new java.util.HashMap<>();
                data.put("user", loginUser);
                data.put("token", token);

                return Result.success(data);
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
