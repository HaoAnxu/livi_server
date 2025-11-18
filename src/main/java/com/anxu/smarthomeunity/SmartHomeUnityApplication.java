package com.anxu.smarthomeunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan//开启对servlet组件的支持，因为要用到过滤器了，FIlter是servlet组件
@SpringBootApplication(exclude = SecurityAutoConfiguration.class,excludeName = "ddlApplicationRunner")
public class SmartHomeUnityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeUnityApplication.class, args);
    }

}
