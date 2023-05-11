package com.guava.deliveryservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
@Configuration
public class OpenApiConfig {

    @Value("${application.swagger.title}")
    private String apiTitle;
    @Value("${application.swagger.description}")
    private String apiDescription;
    @Value("${application.swagger.version}")
    private String apiVersion;

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(apiTitle)
                                 .description(apiDescription)
                                 .version(apiVersion));
    }
}