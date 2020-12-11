/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

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
    private Integer locationId;
}
