/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;

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

    public String getJSONMap() {
        Map departmentJson = new LinkedHashMap();

        departmentJson.put("id", getId());
        departmentJson.put("name", getName());

        Map managerJson = new LinkedHashMap();
        managerJson.put("id", getManagerId());
        if (getManagerId() != null) {
            departmentJson.put("manager", managerJson);
        }

        Map locationJson = new LinkedHashMap();
        locationJson.put("id", getLocationId());
        if (getLocationId() != null) {
            departmentJson.put("location", locationJson);
        }

        return departmentJson.toString();
    }
}
