package com.eking.momp.web.autoconfig;

import com.eking.momp.web.FeignRequestInterceptor;
import com.eking.momp.web.LocaleMessageHolder;
import com.eking.momp.web.UserContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MompWebAutoConfig {

    @Bean
    public UserContext userContext() {
        return new UserContext();
    }

    @Bean
    public LocaleMessageHolder localeMessageUtils() {
        return new LocaleMessageHolder();
    }

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
