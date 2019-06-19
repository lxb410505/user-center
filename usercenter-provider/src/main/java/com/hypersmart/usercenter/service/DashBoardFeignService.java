package com.hypersmart.usercenter.service;


import com.hypersmart.base.conf.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "dashboard-provider-eureka", fallback = DashBoardFeignService.DashBoardFeignServiceImpl.class, configuration = {FeignConfig.class})
public interface DashBoardFeignService {
    @PostMapping({"/dashboard/api/gridBasicInfo/handChangeRange"})
    public void handChangeRange(@RequestParam("gridId")String gridId,@RequestParam("gridRange")String gridRange,@RequestParam("action")Integer action);

    @Component
    public class DashBoardFeignServiceImpl implements DashBoardFeignService {
        public DashBoardFeignServiceImpl() {
        }

        @Override
        public void handChangeRange(String gridId, String gridRange, Integer action) {
        }
    }
}
