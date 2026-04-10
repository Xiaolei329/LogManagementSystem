package com.itheima.logmanagementsystem.service;

import com.itheima.logmanagementsystem.entity.City;
import com.itheima.logmanagementsystem.entity.District;
import com.itheima.logmanagementsystem.entity.Province;

import java.util.List;

public interface RegionService {
    List<Province> findAllProvinces();
    List<City> findCitiesByProvinceId(Integer provinceId);
    List<District> findDistrictsByCityId(Integer cityId);
}
