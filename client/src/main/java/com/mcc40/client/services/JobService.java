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

/**
 *
 * @author aqira
 */
@Service
public class JobService {

    RestTemplate restTemplate;

    @Autowired
    public JobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${api.uri}")
    private String url;
    private String uri = url + "job";
    
    public List<Job> getAll() {
        ResponseEntity<List<Job>> response = restTemplate.exchange(uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Job>>() {
        });
        for (Job job : response.getBody()) {
            System.out.print(job.getJobId());
            System.out.print(job.getJobTitle());
            System.out.print(job.getMaxSalary());
            System.out.println(job.getMinSalary());
        }
        return response.getBody();
    }
}
