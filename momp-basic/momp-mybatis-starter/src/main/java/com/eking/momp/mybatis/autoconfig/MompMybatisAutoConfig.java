package com.eking.momp.mybatis.autoconfig;

import com.eking.momp.mybatis.MybatisMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class MompMybatisAutoConfig {

    @Bean
    @DependsOn("userContext")
    public MybatisMetaObjectHandler mybatisMetaObjectHandler() {
        return new MybatisMetaObjectHandler();
    }
}
