/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Country;
import com.mcc40.client.services.CountryService;
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
 * @author Mocahamad Yusuf
 */
@Controller
@RequestMapping("country")
public class CountryController {

    @Autowired
    CountryService service;

    @GetMapping
    public String table(Model model) {
        model.addAttribute("table", "/country/table.html");
        model.addAttribute("tableFrag", "table");
        model.addAttribute("modal", "/country/form-modal.html");
        model.addAttribute("modalFrag", "form-modal");
        model.addAttribute("javascript", "/js/pages/country.js");
        
        model.addAttribute("htmlTitle", "Country");

        return "/layout/table-page";
    }

    @GetMapping("get-all")
    public @ResponseBody
    List<Country> getAll() {
        System.out.println("fetching country table");
        return service.search(null);
    }

    @PostMapping
    public @ResponseBody
    boolean insert(@RequestBody Country country) {
        return service.insert(country);
    }

    @PutMapping
    public @ResponseBody
    boolean update(@RequestBody Country country) {
        System.out.println("update");

        return service.update(country);
    }

    @DeleteMapping
    public @ResponseBody
    boolean delete(Integer id) {
        System.out.println("delete " + id);

        return service.delete(id);
    }

}
