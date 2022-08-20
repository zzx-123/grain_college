package com.gzuniversity.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGagewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGagewayApplication.class,args);
    }
}
