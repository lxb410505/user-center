package com.hypersmart.usercenter.service;


import com.hypersmart.base.conf.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "dashboard-provider-eureka-lj", fallback = DashBoardFeignService.DashBoardFeignServiceImpl.class, configuration = {FeignConfig.class})
public interface DashBoardFeignService {
    @RequestMapping(value = "/dashboard/api/gridBasicInfo/handChangeRange", method = RequestMethod.POST)
    public void handChangeRange(@RequestParam("gridId")String gridId,@RequestParam("gridRange")String gridRange,@RequestParam("action")Integer action);

    @Component
    public class DashBoardFeignServiceImpl implements DashBoardFeignService {

        @Override
        public void handChangeRange(String gridId, String gridRange, Integer action) {

        }
    }
}
