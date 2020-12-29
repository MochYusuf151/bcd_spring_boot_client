/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.services;

import com.mcc40.client.entities.auth.AccessToken;
import com.mcc40.client.entities.auth.AuthRequest;
import com.mcc40.client.entities.auth.AuthUser;
import java.util.HashMap;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Mochamad Yusuf
 */
@Service
public class UserService {

    RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${api.uri}/user")
    private String uri;

    public AccessToken login(AuthRequest authRequest) {
        ResponseEntity<HashMap> response = restTemplate.postForEntity(uri + "/login", authRequest, HashMap.class);

        String jwt = response.getBody().get("jwt").toString();
        
        AccessToken token = new AccessToken();
        token.setToken(jwt);
        return token;
    }

    public String submitNewPassword(String password, String verificationCode) {
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

    public void getMe(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity("body", headers);

        ResponseEntity<AuthUser> response = restTemplate.exchange(uri + "/me",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<AuthUser>() {
        });

        AuthUser authUser = new AuthUser();
        authUser.setRoles(response.getBody().getRoles());
        authUser.setUsername(response.getBody().getUsername());
        setAuthentication(authUser, token);
    }

    private void setAuthentication(AuthUser authUser, String token) {
        PreAuthenticatedAuthenticationToken auth
                = new PreAuthenticatedAuthenticationToken(authUser.getUsername(), token, authUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
