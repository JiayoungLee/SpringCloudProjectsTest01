package com.hqyj.springcloud.springCloudClientTest.modules.test.service;


import com.github.pagehelper.PageInfo;
import com.hqyj.springcloud.springCloudClientTest.modules.common.vo.Result;
import com.hqyj.springcloud.springCloudClientTest.modules.common.vo.SearchVo;
import com.hqyj.springcloud.springCloudClientTest.modules.test.entity.City;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/11 0011 16:30
 * version 1.0
 */
public interface CityService {
    List<City> getCountryByCountryId(int countryId);

    PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo);

    PageInfo<City> getCitiesBySearchVo(SearchVo searchVo);

    Result<City> insertCity(City city);

    Result<City> updateCity(City city);

    Result<Object> deleteCity(int cityId);
}
