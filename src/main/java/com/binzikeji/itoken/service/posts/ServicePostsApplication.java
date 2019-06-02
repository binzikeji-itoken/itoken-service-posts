package com.binzikeji.itoken.service.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Description
 * @Author Bin
 * @Date 2019/4/25 14:19
 **/
@EnableSwagger2
@SpringBootApplication(scanBasePackages = "com.binzikeji.itoken")
@EnableEurekaClient
@MapperScan(basePackages = {"com.binzikeji.itoken.service.posts.mapper", "com.binzikeji.itoken.common.mapper"})
public class ServicePostsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePostsApplication.class, args);
    }
}
