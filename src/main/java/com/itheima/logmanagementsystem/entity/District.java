package com.itheima.logmanagementsystem.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class District {
    private Integer districtId;
    private String districtName;
    private Integer cityId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
