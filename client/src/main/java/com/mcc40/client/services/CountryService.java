/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.Country;
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
public class CountryService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.uri}/countries")
    private String url;

    public List<Country> search(String keyword) {
        String url = this.url;
        if (keyword != null && !keyword.isEmpty()) {
            url += "?keyword=" + keyword;
        }
        ResponseEntity<List<Country>> response = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Country>>() {
        });

        return response.getBody();
    }

    public boolean insert(Country country) {
        System.out.println("insert");

        HttpEntity<String> request
                = new HttpEntity<String>(country.getJSONMap());

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

    public boolean update(Country country) {
        System.out.println("update");

        HttpEntity<String> request
                = new HttpEntity<String>(country.getJSONMap());

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
