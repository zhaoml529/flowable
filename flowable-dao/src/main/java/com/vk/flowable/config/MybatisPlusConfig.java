package com.vk.flowable.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.ourhours.ticket.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        return new PaginationInterceptor();
    }

    /**
     * 开启乐观锁更新
     * @return
     */
    /*@Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor () {

        return new OptimisticLockerInterceptor();
    }*/
}
