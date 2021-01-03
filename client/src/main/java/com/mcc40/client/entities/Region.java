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
 * @author Mochamad Yusuf
 */
@Data
public class Region {

    private Integer id;
    private String name;

    public String getJSONMap() {
        JSONObject departmentJson = new JSONObject();

        departmentJson.put("id", getId());
        departmentJson.put("name", getName());

        return departmentJson.toString();
    }
}
