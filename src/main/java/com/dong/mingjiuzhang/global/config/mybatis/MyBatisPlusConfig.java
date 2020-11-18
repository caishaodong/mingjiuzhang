package com.dong.mingjiuzhang.global.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author caishaodong
 * @Date 2020-08-06 13:03
 * @Description
 **/
@Configuration
public class MyBatisPlusConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyBatisPlusConfig.class);

    /**
     * 注册分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        LOGGER.debug("注册分页插件");
        return new PaginationInterceptor();
    }

}
