package com.itheima.logmanagementsystem.mapper;

import com.itheima.logmanagementsystem.entity.City;
import com.itheima.logmanagementsystem.entity.District;
import com.itheima.logmanagementsystem.entity.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionMapper {
    List<Province> findAllProvinces();
    List<City> findCitiesByProvinceId(Integer provinceId);
    List<District> findDistrictsByCityId(Integer cityId);
}
