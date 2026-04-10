package com.itheima.logmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class LogManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogManagementSystemApplication.class, args);
        System.out.println("系统启动成功！！！！");
    }

}
