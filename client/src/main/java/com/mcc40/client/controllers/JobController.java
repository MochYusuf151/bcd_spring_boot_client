/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Job;
import com.mcc40.client.entities.Employee;
import com.mcc40.client.services.JobService;
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
 * @author Mochamad Yusuf
 */
@Controller
@RequestMapping("job")
public class JobController {

    @Autowired
    JobService service;

    @GetMapping
    public String table(Model model) {
        model.addAttribute("table", "/job/table.html");
        model.addAttribute("tableFrag", "table");
        model.addAttribute("modal", "/job/form-modal.html");
        model.addAttribute("modalFrag", "form-modal");
        model.addAttribute("javascript", "/js/pages/job.js");
        
        model.addAttribute("htmlTitle", "Job");

        return "/layout/table-page";
    }

    @GetMapping("get-all")
    public @ResponseBody
    List<Job> getAll() {
        System.out.println("fetching job table");
        return service.search(null);
    }

    @PostMapping
    public @ResponseBody
    boolean insert(@RequestBody Job job) {
        return service.insert(job);
    }

    @PutMapping
    public @ResponseBody
    boolean update(@RequestBody Job job) {
        System.out.println("update");

        return service.update(job);
    }

    @DeleteMapping
    public @ResponseBody
    boolean delete(String id) {
        System.out.println("delete " + id);

        return service.delete(id);
    }

}
