package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;


import java.io.Serializable;

@Data
public class City implements Serializable {
    private Integer cityId;
    private String cityName;
    private String zipCode;
    private Integer provinceId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
