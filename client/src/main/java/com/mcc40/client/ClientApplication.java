package com.mcc40.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        System.out.println("Client is started");
    }

    @Bean //IOC
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
