package com.szjz.mybatisplusstudy.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author:szjz
 * date:2019/7/26
 */

@Configuration
public class MybatisPlusConfig {

    /** 使用分页插件之前必须先配置分页拦截器 */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
