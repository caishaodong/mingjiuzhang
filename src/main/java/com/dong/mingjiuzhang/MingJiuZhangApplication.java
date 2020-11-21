package com.dong.mingjiuzhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan(basePackages = "com.dong.mingjiuzhang.mapper")
@EnableAsync
public class MingJiuZhangApplication {

    public static void main(String[] args) {
        SpringApplication.run(MingJiuZhangApplication.class, args);
    }

}
