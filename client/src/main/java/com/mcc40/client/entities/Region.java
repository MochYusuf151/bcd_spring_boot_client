/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author Mochamad Yusuf
 */
@Data
public class Region {

    private Integer id;
    private String name;

    public String getJSONMap() {
        Map departmentJson = new LinkedHashMap();

        departmentJson.put("id", getId());
        departmentJson.put("name", getName());

        return departmentJson.toString();
    }
}
