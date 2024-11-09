package com.acm.apirestful.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
public class LibraryConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return builder.messageConverters(converter).build();
    }

    @Bean
    @Scope("prototype")
    public HttpClient getHttpClient(){
        return HttpClient.newHttpClient();
    }

}
