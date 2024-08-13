package com.emre.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()
                .info(new Info().title("Blog Yonetim Sistemi")
                        .version("1.0")
                        .description("Blog Yonetim Sistemi API Documentation")
                        .termsOfService("http://swagger.oi/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact()
                                .name("Emre Kocaman")
                                .email("emre.kocaman@hotmail.com")));
    }
}
