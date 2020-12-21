/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mochamad Yusuf
 */
@Controller
@RequestMapping("user")
public class UserController {

    UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }
    
    @GetMapping("auth")
    public String auth() {
        return "auth";
    }

    @GetMapping("forgot-password/{verificationCode}")
    public String forgotPassword(@PathVariable String verificationCode, Model model) {
        model.addAttribute("verificationCode", verificationCode);
        return "reset_password";
    }

    @PostMapping("reset-password/{verificationCode}")
    public String resetPassword(@PathVariable String verificationCode,
            String password,
            String retypePassword,
            Model model) {
        if (password.equals(retypePassword)) {
            service.submitNewPassword(password, verificationCode);
            return "reset_confirmed";
        } else {
            return "error";
        }
    }
}
