/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Employee;
import com.mcc40.client.services.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author WAHYUK
 */
@Controller
@RequestMapping("operator")
public class EmployeeController {
    
    @Autowired
    private EmployeeService service;
    
    @GetMapping
    public String dashboard() {
        List<Employee> employees = service.search("");
        for (Employee employee : employees) {
            System.out.println(employee.getFirstName());
        }
        return "operator";
    }
}
