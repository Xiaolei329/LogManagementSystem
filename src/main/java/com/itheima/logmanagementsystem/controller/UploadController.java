package com.itheima.logmanagementsystem.controller;

import com.itheima.logmanagementsystem.common.Result;
import com.itheima.logmanagementsystem.utils.AliOssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UploadController {

    @Autowired
    private AliOssUtils aliOssUtils;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile image) {
        try {
            // 阿里云对象存储服务
            String url = aliOssUtils.upload(image);
            return Result.success(url);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件上传失败: " + e.getMessage());
        }
    }
}
