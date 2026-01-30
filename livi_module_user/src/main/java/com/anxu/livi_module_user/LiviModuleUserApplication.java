package com.anxu.livi_module_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

//@ServletComponentScan//开启对servlet组件的支持，因为要用到过滤器了，FIlter是servlet组件
@EnableScheduling//开启定时任务
@SpringBootApplication(exclude = SecurityAutoConfiguration.class,excludeName = "ddlApplicationRunner")
public class LiviModuleUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiviModuleUserApplication.class, args);
    }
}
