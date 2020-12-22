/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Department;
import com.mcc40.client.services.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author aqira
 */
@Controller
@RequestMapping("department")
public class DepartmentController {

    DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }
    
    @GetMapping("")
    public String search(String keyword, Model model) {
        List<Department> departments = service.search(keyword);
        model.addAttribute("departments", departments);
        return "department/department_table";
    }

    @GetMapping("edit")
    public String openEditPage(Model model) {
        return "department/department_edit";
    }
    
    @PostMapping("modify")
    public String modifyDepartment(@RequestParam("id") Integer id, Model model) {
        System.out.println("modify param: "  + id);
        Department department = service.getById(id).get(0);
        model.addAttribute("department", department);
        return "department/department_modify";
    }
    
    @GetMapping("modify")
    public String openSavePage(Model model) {
        return "department/department_modify";
    }

    @PostMapping("modify/post")
    public String savePost(String id, String name, String manager_id, String location_id, Model model) {
        System.out.println("[POST] department: " + id + " | " + name + " | " + manager_id + " | " + location_id);
        Department department = new Department();
        department.setId(Integer.parseInt(id));
        department.setName(name);
        department.setManagerId(Integer.parseInt(manager_id));
        department.setLocationId(Integer.parseInt(location_id));

        System.out.println(department);
        service.savePost(department);
        return "redirect:localhost:8082/department/modify";
    }

    @PostMapping("modify/put")
    public String savePut(String id, String name, String manager_id, String location_id, Model model) {
        System.out.println("[PUT] department: " + id + " | " + name + " | " + manager_id + " | " + location_id);
        Department department = new Department();
        department.setId(Integer.parseInt(id));
        if (!name.equals("")) {
            department.setName(name);
        }
        if (!manager_id.equals("")) {
            department.setManagerId(Integer.parseInt(manager_id));
        }
        if (!location_id.equals("")) {
            department.setLocationId(Integer.parseInt(location_id));
        }

        System.out.println(department);
        service.savePut(department);
        return "redirect:localhost:8082/department/modify";
    }
    
    @PostMapping("modify/delete")
    public String savePut(String id, Model model) {
        System.out.println("[DELETE] department: " +  id);
        
        service.deleteById(Integer.parseInt(id));
        
        return "redirect:localhost:8082/department/modify";
    }

}
