/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author WAHYUK
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController {
    
    @GetMapping
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        for (GrantedAuthority authority : auth.getAuthorities()) {
            System.out.println(authority.getAuthority());
            System.out.println(authority);
        }
        model.addAttribute("profile", auth);
        
        return "/dashboard";
    }
}
