package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;

@Data
public class District implements Serializable {
    private Integer districtId;
    private String districtName;
    private Integer cityId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
