/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import org.json.JSONObject;

/**
 *
 * @author Mochamad Yusuf
 */
@Data
public class Location{

    private Integer id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    private String country;
    
    @JsonAnyGetter
    public Map<String, Object> getJSONMap() {
        Map locationJson = new LinkedHashMap();

        if (getCountry() != null) {
            Map managerJson = new LinkedHashMap();
            managerJson.put("id", getCountry());
            locationJson.put("country", managerJson);
        }

        return locationJson;
    }
}

