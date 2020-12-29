package com.mcc40.client.config.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mochamad Yusuf
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors.isEmpty()) {
            interceptors = new ArrayList<>();
        }

        interceptors.add(new RequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        restTemplate.setErrorHandler(new RestErrorHandler());
        return restTemplate;
    }
}
