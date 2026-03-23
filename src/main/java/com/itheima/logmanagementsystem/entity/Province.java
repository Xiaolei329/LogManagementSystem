package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Province {
    private Integer provinceId;
    private String provinceName;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
