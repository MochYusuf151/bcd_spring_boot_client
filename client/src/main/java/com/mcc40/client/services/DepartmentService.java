/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.Department;
import com.mcc40.client.entities.Employee;
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
public class DepartmentService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.uri}/departments")
    private String url;

    public List<Department> search(String keyword) {
        String url = this.url;
        if (keyword != null) {
            url += "?keyword=" + keyword;
        }
        ResponseEntity<List<Department>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Department>>() {
        });

        return response.getBody();
    }

    public List<Department> getById(Integer id) {
        String url = this.url;
        if (id != null) {
            url += "?id=" + id;
        }
        ResponseEntity<List<Department>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Department>>() {
        });

        return response.getBody();
    }

    public boolean insert(Department department) {
        System.out.println(department.getJSONString());

        ResponseEntity<String> response = restTemplate.postForEntity(url, department.getJSONString(), String.class);

        return response.getStatusCodeValue() == 200;
    }

    public boolean update(Department department) {
        System.out.println("update");

        HttpEntity<String> request
                = new HttpEntity<String>(department.getJSONString());

        System.out.println(department.getJSONString());

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                String.class
        );
        System.out.println("response: " + response.getBody());

        return response.getStatusCodeValue() == 200;
    }

    public boolean delete(Integer id) {
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
