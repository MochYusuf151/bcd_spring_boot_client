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
public class Country {

    private String id;
    private String name;
    private Integer region;

    public String getJSONMap() {
        JSONObject countryJson = new JSONObject();

        countryJson.put("id", getId());
        countryJson.put("name", getName());
        countryJson.put("regionId", getRegion());

        return countryJson.toString();
    }
}
