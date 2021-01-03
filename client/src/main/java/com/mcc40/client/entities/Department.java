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

    public String getJSONMap() {
        JSONObject departmentJson = new JSONObject();

        departmentJson.put("id", getId());
        departmentJson.put("name", getName());

        JSONObject managerJson = new JSONObject();
        managerJson.put("id", getManagerId());
        if (getManagerId() != null) {
            departmentJson.put("manager", managerJson);
        }

        JSONObject locationJson = new JSONObject();
        locationJson.put("id", getLocationId());
        if (getLocationId() != null) {
            departmentJson.put("location", locationJson);
        }

        return departmentJson.toString();
    }
}
