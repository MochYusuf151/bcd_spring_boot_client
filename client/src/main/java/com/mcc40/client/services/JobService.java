/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.Job;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;

/**
 *
 * @author Mochamad Yusuf
 */
@Service
public class JobService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.uri}/jobs")
    private String url;

    public List<Job> search(String keyword) {
        String url = this.url;
        if (keyword != null && !keyword.isEmpty()) {
            url += "?keyword=" + keyword;
        }
        ResponseEntity<List<Job>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Job>>() {
        });

        return response.getBody();
    }

    public boolean insert(Job job) {
        System.out.println("insert");

        HttpEntity<Job> request
                = new HttpEntity<Job>(job);

        System.out.println(request.getBody());

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
        System.out.println("response: " + response.getBody());

        return response.getStatusCodeValue() == 200;
    }

    public boolean update(Job job) {
        System.out.println("update");

        HttpEntity<Job> request
                = new HttpEntity<Job>(job);

        System.out.println(request.getBody());

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                String.class
        );
        System.out.println("response: " + response.getBody());

        return response.getStatusCodeValue() == 200;
    }

    public boolean delete(String id) {
        String url = this.url + "?id=" + id;

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                String.class
        );

        return response.getStatusCodeValue() == 200;
    }

}
