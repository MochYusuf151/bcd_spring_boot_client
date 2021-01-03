/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Region;
import com.mcc40.client.entities.Employee;
import com.mcc40.client.entities.Location;
import com.mcc40.client.services.RegionService;
import com.mcc40.client.services.EmployeeService;
import com.mcc40.client.services.LocationService;
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
@RequestMapping("region")
public class RegionController {

    @Autowired
    RegionService service;

    @GetMapping
     public String table(Model model) {
        model.addAttribute("table", "/region/table.html");
        model.addAttribute("tableFrag", "table");
        model.addAttribute("modal", "/region/form-modal.html");
        model.addAttribute("modalFrag", "form-modal");
        model.addAttribute("javascript", "/js/pages/region.js");
        
        model.addAttribute("htmlTitle", "Region");

        return "/layout/table-page";
    }

    @GetMapping("get-all")
    public @ResponseBody
    List<Region> getAll() {
        System.out.println("fetching region table");
        return service.search(null);
    }

    @PostMapping
    public @ResponseBody
    boolean insert(@RequestBody Region region) {
        return service.insert(region);
    }

    @PutMapping
    public @ResponseBody
    boolean update(@RequestBody Region region) {
        System.out.println("update");
        
        return service.update(region);
    }
    
    @DeleteMapping
    public @ResponseBody
    boolean delete(Integer id) {
        System.out.println("delete " + id);
        
        return service.delete(id);
    }

}
