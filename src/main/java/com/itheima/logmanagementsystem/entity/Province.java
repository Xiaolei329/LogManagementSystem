package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;

@Data
public class Province implements Serializable {
    private Integer provinceId;
    private String provinceName;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
