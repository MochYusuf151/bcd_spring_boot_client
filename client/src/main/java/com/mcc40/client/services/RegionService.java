/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.Region;
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
public class RegionService {

    RestTemplate restTemplate;

    @Autowired
    public RegionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${api.uri}")
    private String url;

    public List<Region> search(String keyword) {
        String uri = this.url + "regions";
        if (keyword != null) {
            uri += "?keyword=" + keyword;
        }
        ResponseEntity<List<Region>> response = restTemplate.exchange(uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Region>>() {
        });
        for (Region region : response.getBody()) {
            System.out.print(region.getId());
            System.out.print(region.getName());
//            System.out.print(region.getManager_id());
//            System.out.println(region.getLocation_id());
        }
        return response.getBody();
    }

    public String savePost(Region region) {
        String uri = this.url + "regions";

        // Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Json Object
        JSONObject regionJson = new JSONObject();

        regionJson.put("id", region.getId());
        regionJson.put("name", region.getName());

        //Http Entity
        HttpEntity<String> request
                = new HttpEntity<String>(regionJson.toString(), headers);

        System.out.println(regionJson.toString());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        return response.getBody();
    }

    public String savePut(Region region) {
        String uri = this.url + "regions";

        // Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Json Object
        JSONObject regionJson = new JSONObject();

        regionJson.put("id", region.getId());
        if (region.getName() != null) {
            regionJson.put("name", region.getName());
        }

        //Http Entity
        HttpEntity<String> request
                = new HttpEntity<String>(regionJson.toString(), headers);

        System.out.println(regionJson.toString());

        restTemplate.put(uri, request);

        return "Success";
    }
    
    public String deleteById(Integer id) {
        String uri = this.url + "regions?id=" + id;

        restTemplate.delete(uri, String.class);

        return "Success";
    }
    

}
