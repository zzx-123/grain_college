package com.gzuniversity.educms.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.gzuniversity.educms.mapper")
public class CmsConfig {
}
