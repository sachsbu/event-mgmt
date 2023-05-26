package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestTemplateConfig {

    private static final String BASE_URL = "https://tm-event-mgmt.s3.eu-west-2.amazonaws.com";
    @Bean
    public RestTemplate getRestTemplate(){
        return  new RestTemplate();
    }


    @Bean
    public WebClient getWebClient(){
        return WebClient.create(BASE_URL);
    }

}
