package com.itheima.logmanagementsystem.controller;

import com.itheima.logmanagementsystem.common.Result;
import com.itheima.logmanagementsystem.entity.City;
import com.itheima.logmanagementsystem.entity.District;
import com.itheima.logmanagementsystem.entity.Province;
import com.itheima.logmanagementsystem.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/region")
@CrossOrigin
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/provinces")
    public Result<List<Province>> getProvinces() {
        return Result.success(regionService.findAllProvinces());
    }

    @GetMapping("/cities/{provinceId}")
    public Result<List<City>> getCities(@PathVariable Integer provinceId) {
        return Result.success(regionService.findCitiesByProvinceId(provinceId));
    }

    @GetMapping("/districts/{cityId}")
    public Result<List<District>> getDistricts(@PathVariable Integer cityId) {
        return Result.success(regionService.findDistrictsByCityId(cityId));
    }
}
