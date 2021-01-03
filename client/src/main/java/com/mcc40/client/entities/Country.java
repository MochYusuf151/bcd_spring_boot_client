/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import java.util.List;
import lombok.Data;

/**
 *
 *
 */
@Data
public class Country {

    private String id;
    private String name;
    private Integer regionId;
    private String regionName;
}
