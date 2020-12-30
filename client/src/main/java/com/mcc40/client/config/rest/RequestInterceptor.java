/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.config.rest;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Mochamad Yusuf
 */
public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest req, byte[] body, ClientHttpRequestExecution res) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth.isAuthenticated()){
            req.getHeaders().add("Authorization", "Bearer " + auth.getCredentials().toString());
            req.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        }
        
        ClientHttpResponse response = res.execute(req, body);
        return response;
    }
    
}
