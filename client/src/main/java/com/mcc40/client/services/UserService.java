/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.ChangePasswordData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mochamad Yusuf
 */
@Service
public class ChangePasswordService {
    RestTemplate restTemplate;

    @Autowired
    public ChangePasswordService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Value("${api.uri}")
    private String uri;
    
    public String submitNewPassword(String password, String verificationCode){
        String uri = this.uri + "users/reset-password/" + verificationCode;

        // Http Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Json Object
        JSONObject passwordJson = new JSONObject();

        passwordJson.put("password", password);

        //Http Entity
        HttpEntity<String> request
                = new HttpEntity<String>(passwordJson.toString(), headers);

        System.out.println(passwordJson.toString());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        return response.getBody();
    }
    
}
