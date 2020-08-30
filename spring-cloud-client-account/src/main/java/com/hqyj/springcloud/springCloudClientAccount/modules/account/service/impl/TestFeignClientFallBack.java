package com.hqyj.springcloud.springCloudClientAccount.modules.account.service.impl;

import com.hqyj.springcloud.springCloudClientAccount.modules.account.entity.City;
import com.hqyj.springcloud.springCloudClientAccount.modules.account.service.TestFeignClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/28 0028 11:18
 * version 1.0
 */
@Component
public class TestFeignClientFallBack implements TestFeignClient {
    @Override
    public List<City> getCitiesByCountryId(int countryId) {
        return new ArrayList<City>();
    }
}
