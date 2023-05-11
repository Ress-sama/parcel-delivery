package com.guavapay.gateway.configuration;

import com.guavapay.gateway.error.GlobalErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.guavapay.gateway.service")
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GlobalErrorDecoder();
    }

}
