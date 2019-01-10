package com.hypersmart.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

//@EnableDiscoveryClient
//@SpringCloudApplication
//@Configuration
//@MapperScan(basePackages = {"com.hypersmart.usercenter.mapper"})
//@ComponentScan({"com.hypersmart.*"})
//@EnableFeignClients(basePackages = {"com.hypersmart.*"})
@EnableDiscoveryClient
@SpringCloudApplication
@Configuration
@MapperScan(basePackages = {"com.hypersmart.usercenter.mapper"})
@ComponentScan({ "com.hypersmart.*" })
@EnableFeignClients(basePackages = { "com.hypersmart.*" })
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}



