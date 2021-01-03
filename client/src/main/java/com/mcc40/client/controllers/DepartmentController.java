/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Department;
import com.mcc40.client.entities.Employee;
import com.mcc40.client.services.DepartmentService;
import com.mcc40.client.services.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String table(Model model) {
        model.addAttribute("table", "/department/table.html");
        model.addAttribute("tableFrag", "table");
        model.addAttribute("modal", "/department/form-modal.html");
        model.addAttribute("modalFrag", "form-modal");
        model.addAttribute("javascript", "/js/pages/department.js");
        
        model.addAttribute("htmlTitle", "Department");

        return "/layout/table-page";
    }

    @GetMapping("get-all")
    public @ResponseBody
    List<Department> getAll() {
        System.out.println("fetching department table");
        return service.search(null);
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
