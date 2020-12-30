/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Department;
import com.mcc40.client.entities.Employee;
import com.mcc40.client.entities.SelectData;
import com.mcc40.client.services.DepartmentService;
import com.mcc40.client.services.EmployeeService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author aqira
 */
@Controller
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    DepartmentService service;
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public String table() {
        return "/department/table";
    }

    @GetMapping("get-all")
    public @ResponseBody
    List<Department> getAll() {
        System.out.println("fetching department table");
        return service.search(null);
    }
    
    @GetMapping("get-managers")
    public @ResponseBody
    List<Employee> getManagers() {
        System.out.println("fetching employee table");
        List<Employee> employees = employeeService.search(null);
        return employees;
    }
    
    @GetMapping("get-example")
    public @ResponseBody
    List<SelectData> getExample() {
        List<SelectData> s = new ArrayList<>();
        s.add(new SelectData(1,"Feby"));
        s.add(new SelectData(2,"Fery"));
        s.add(new SelectData(3,"Rosa"));
        s.add(new SelectData(4,"Remi"));
        s.add(new SelectData(5,"Renda"));
        s.add(new SelectData(6,"Jeni"));
        s.add(new SelectData(7,"Abdul"));
        return s;
    }

    @PostMapping
    public @ResponseBody
    boolean insert(@RequestBody Department department) {
        return service.insert(department);
    }

    @PutMapping
    public @ResponseBody
    boolean update(@RequestBody Department department) {
        System.out.println("update");
        
        return service.update(department);
    }
    
    
    @DeleteMapping
    public @ResponseBody
    boolean delete(Integer id) {
        System.out.println("delete " + id);
        
        return service.delete(id);
    }

}
