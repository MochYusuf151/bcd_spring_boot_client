/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.Employee;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author WAHYUK
 */
@Service
public class EmployeeService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.uri}/employee")
    private String uri;

    public List<Employee> search(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String url = uri + "/search?keyword=";
        if (keyword != null) {
            url += keyword;
        }

        ResponseEntity<List<Employee>> response = restTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
        });

        employees = response.getBody();
        return employees;
    }
}
