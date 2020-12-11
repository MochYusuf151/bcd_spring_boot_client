/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.Department;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 *
 * @author Mochamad Yusuf
 */
@Service
public class DepartmentService {

    RestTemplate restTemplate;

    @Autowired
    public DepartmentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${api.uri}")
    private String url;

    public List<Department> search(String keyword) {
        String uri = this.url + "departments";
        if (keyword != null) {
            uri += "?keyword=" + keyword;
        }
        ResponseEntity<List<Department>> response = restTemplate.exchange(uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Department>>() {
        });
        for (Department department : response.getBody()) {
            System.out.print(department.getId());
            System.out.print(department.getName());
//            System.out.print(department.getManager_id());
//            System.out.println(department.getLocation_id());
        }
        return response.getBody();
    }

    public String savePost(Department department) {
        String uri = this.url + "departments";

        // Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Json Object
        JSONObject departmentJson = new JSONObject();
        JSONObject managerJson = new JSONObject();
        managerJson.put("id", department.getManagerId());

        JSONObject locationJson = new JSONObject();
        locationJson.put("id", department.getLocationId());

        departmentJson.put("manager", managerJson);
        departmentJson.put("location", locationJson);

        departmentJson.put("id", department.getId());
        departmentJson.put("name", department.getName());

        //Http Entity
        HttpEntity<String> request
                = new HttpEntity<String>(departmentJson.toString(), headers);

        System.out.println(departmentJson.toString());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        return response.getBody();
    }

    public String savePut(Department department) {
        String uri = this.url + "departments";

        // Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Json Object
        JSONObject departmentJson = new JSONObject();
        JSONObject managerJson = new JSONObject();
        managerJson.put("id", department.getManagerId());

        JSONObject locationJson = new JSONObject();
        locationJson.put("id", department.getLocationId());
        if (department.getManagerId() != null) {
            departmentJson.put("manager", managerJson);
        }
        if (department.getLocationId() != null) {
            departmentJson.put("location", locationJson);
        }

        departmentJson.put("id", department.getId());
        if (department.getName() != null) {
            departmentJson.put("name", department.getName());
        }

        //Http Entity
        HttpEntity<String> request
                = new HttpEntity<String>(departmentJson.toString(), headers);

        System.out.println(departmentJson.toString());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        return response.getBody();
    }
    
    public String deleteById(Integer id) {
        String uri = this.url + "departments?id=" + id;

        restTemplate.delete(uri, String.class);

        return "Success";
    }
    

}
