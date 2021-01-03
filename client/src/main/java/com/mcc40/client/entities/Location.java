/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import org.json.JSONObject;

/**
 *
 * @author Mochamad Yusuf
 */
@Data
public class Location {

    private Integer id;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String stateProvince;
    private String country;

    public String getJSONMap() {
        JSONObject locationJson = new JSONObject();
        
        locationJson.put("id", getId());
        locationJson.put("streetAddress", getStreetAddress());
        locationJson.put("postalCode", getPostalCode());
        locationJson.put("city", getCity());
        locationJson.put("stateProvince", getStateProvince());

        Map countryJson = new LinkedHashMap();
        if (getCountry() != null) {
            countryJson.put("id", getCountry());
        }
        locationJson.put("country", countryJson);

        return locationJson.toString();
    }
}
