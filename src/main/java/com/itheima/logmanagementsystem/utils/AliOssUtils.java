package com.itheima.logmanagementsystem.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOssUtils {

    @Value("${alioss.endpoint}")
    private String endpoint;

    @Value("${alioss.access-key-id}")
    private String accessKeyId;

    @Value("${alioss.access-key-secret}")
    private String accessKeySecret;

    @Value("${alioss.bucket-name}")
    private String bucketName;

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();

        // 避免文件覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = "avatars/" + fileName;

        // 创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build("https://" + endpoint, accessKeyId, accessKeySecret);

        try {
            // 上传文件到指定的 Bucket
            ossClient.putObject(bucketName, objectName, inputStream);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        // 把上传到 OSS 的公开访问路径返回
        String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
        return url;
    }
}
