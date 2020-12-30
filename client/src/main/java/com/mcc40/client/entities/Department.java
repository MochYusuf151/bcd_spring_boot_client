/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import lombok.Data;
import org.json.JSONObject;

/**
 *
 *
 */
@Data
public class Department {
    private Integer id;
    private String name;
    private Integer managerId;
    private String manager;
    private Integer locationId;
    private String location;
    
    public String getJSONString(){
        JSONObject departmentJson = new JSONObject();
        JSONObject managerJson = new JSONObject();
        managerJson.put("id", getManagerId());

        JSONObject locationJson = new JSONObject();
        locationJson.put("id", getLocationId());
        if (getManagerId() != null) {
            departmentJson.put("manager", managerJson);
        }
        if (getLocationId() != null) {
            departmentJson.put("location", locationJson);
        }

        departmentJson.put("id", getId());
        if (getName() != null) {
            departmentJson.put("name", getName());
        }
        
        return departmentJson.toString();
    }
}
