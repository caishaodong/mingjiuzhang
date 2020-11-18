package com.dong.mingjiuzhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.dong.mingjiuzhang.mapper")
public class MingJiuZhangApplication {

    public static void main(String[] args) {
        SpringApplication.run(MingJiuZhangApplication.class, args);
    }

}
