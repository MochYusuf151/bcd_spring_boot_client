/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.auth.AccessToken;
import com.mcc40.client.entities.auth.AuthRequest;
import com.mcc40.client.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author WAHYUK
 */
@Controller
public class LoginController {

    @Autowired
    private UserService service;

    @GetMapping("login")
    public String login() {
        String result = "";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equalsIgnoreCase("anonymousUser")) {
            result = "redirect:/dashboard";
        }else{
            result = "login";
        }
        return result;
    }

    @GetMapping("login?error")
    public String loginError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "Invalid username or password");
        return "redirect:/login";
    }

    @PostMapping("auth")
    public String auth(String username, String password) {
        System.out.println("Request authentication");
        AccessToken token = service.login(new AuthRequest(username, password));
        
        System.out.println(token.getToken());
        
        if (!token.getToken().isEmpty()) {
            System.out.println("Dapat token");
            service.getMe(token.getToken());
            return "redirect:/dashboard";
        }
        System.out.println("Token failed");
        return "/login?error";
    }
    
    @PostMapping("logout")
    public String logout() {
        return "redirect:/login";
    }
}
