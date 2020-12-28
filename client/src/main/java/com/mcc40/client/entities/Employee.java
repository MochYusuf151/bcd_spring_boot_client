/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import lombok.Data;

/**
 *
 * @author WAHYUK
 */
@Data
public class Employee {
    private String firstName;
    private String lastName;
    private String hireDate;
    private String phoneNumber;
    private int manager;
    private int id;
    private String job;
    private double salary;
    private int department;
    private String email;
    private String commissionPct;
}
