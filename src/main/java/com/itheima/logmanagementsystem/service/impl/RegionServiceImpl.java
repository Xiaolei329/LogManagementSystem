package com.itheima.logmanagementsystem.service.impl;

import com.itheima.logmanagementsystem.entity.City;
import com.itheima.logmanagementsystem.entity.District;
import com.itheima.logmanagementsystem.entity.Province;
import com.itheima.logmanagementsystem.mapper.RegionMapper;
import com.itheima.logmanagementsystem.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    @Cacheable(value = "regionCache", key = "'allProvinces'")
    public List<Province> findAllProvinces() {
        System.out.println("从数据库查询所有省份放入 Redis 缓存。");
        return regionMapper.findAllProvinces();
    }

    @Override
    @Cacheable(value = "regionCache", key = "'cities:province:' + #provinceId")
    public List<City> findCitiesByProvinceId(Integer provinceId) {
        System.out.println("从数据库查询市区放入 Redis 缓存, provinceId: " + provinceId);
        return regionMapper.findCitiesByProvinceId(provinceId);
    }

    @Override
    @Cacheable(value = "regionCache", key = "'districts:city:' + #cityId")
    public List<District> findDistrictsByCityId(Integer cityId) {
        System.out.println("从数据库查询县区放入 Redis 缓存, cityId: " + cityId);
        return regionMapper.findDistrictsByCityId(cityId);
    }
}
