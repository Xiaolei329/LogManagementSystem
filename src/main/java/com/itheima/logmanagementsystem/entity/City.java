package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class City {
    private Integer cityId;
    private String cityName;
    private String zipCode;
    private Integer provinceId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
