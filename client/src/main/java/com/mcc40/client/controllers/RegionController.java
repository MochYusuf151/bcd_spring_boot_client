/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.controllers;

import com.mcc40.client.entities.Region;
import com.mcc40.client.services.RegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author aqira
 */
@Controller
@RequestMapping("region")
public class RegionController {

    RegionService service;

    @Autowired
    public RegionController(RegionService service) {
        this.service = service;
    }

    @GetMapping("")
    public String search(String keyword, Model model) {
        List<Region> regions = service.search(keyword);
        model.addAttribute("regions", regions);
        return "region/region_table";
    }

    @GetMapping("modify")
    public String openSavePage(Model model) {
        return "region/region_edit";
    }

    @PostMapping("post")
    public String savePost(String id, String name, Model model) {
        System.out.println("[POST] region: " + id + " | " + name);
        Region region = new Region();
        region.setId(Integer.parseInt(id));
        region.setName(name);

        System.out.println(region);
        service.savePost(region);
        return "redirect:localhost:8082/region/modify";
    }

    @PostMapping("update")
    public String savePut(Integer id, String name, Model model) {
        System.out.println("[PUT] region: " + id + " | " + name);
        Region region = new Region();
        region.setId(id);
        if (!name.equals("")) {
            region.setName(name);
        }

        System.out.println(region);
        service.savePut(region);
        return "redirect:localhost:8082/region/modify";
    }
    
    @PostMapping("delete")
    public String delete(String id, Model model) {
        System.out.println("[DELETE] region: " +  id);
        
        service.deleteById(Integer.parseInt(id));
        
        return "redirect:localhost:8082/region/modify";
    }

}
