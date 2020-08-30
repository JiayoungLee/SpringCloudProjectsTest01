package com.hqyj.springcloud.springCloudClientAccount.modules.account.service;

import com.hqyj.springcloud.springCloudClientAccount.modules.account.entity.City;
import com.hqyj.springcloud.springCloudClientAccount.modules.account.service.impl.TestFeignClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * author  Jayoung
 * createDate  2020/8/28 0028 10:25
 * version 1.0
 */
@FeignClient(value = "CLIENT-TEST",fallback = TestFeignClientFallBack.class)
@Primary
public interface TestFeignClient {

    @GetMapping("/api/cities/{countryId}")
    List<City> getCitiesByCountryId(@PathVariable(value = "countryId") int countryId);
}
