package com.gzuniversity.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.gzuniversity"})
@MapperScan("com.gzuniversity.eduorder.mapper")
public class OrdersApplication {

}
