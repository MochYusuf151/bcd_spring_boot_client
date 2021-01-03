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

@Data
public class Employee {

    private Integer id;
    private String firstName;
    private String lastName;
    private String hireDate;
    private String phoneNumber;
    private Double salary;
    private String email;
    private Double commissionPct;

    private String job;
    private Integer manager;
    private Integer department;

    public String getJSONMap() {
        JSONObject employeeJson = new JSONObject();

        employeeJson.put("id", getId());
        employeeJson.put("firstName", getFirstName());
        employeeJson.put("lastName", getLastName());
        employeeJson.put("hireDate", getHireDate());
        employeeJson.put("phoneNumber", getPhoneNumber());
        employeeJson.put("salary", getSalary());
        employeeJson.put("email", getEmail());
        employeeJson.put("commissionPct", getCommissionPct());

        JSONObject jobJson = new JSONObject();
        jobJson.put("id", getJob());
        if (getJob() != null) {
            employeeJson.put("job", jobJson);
        }
        JSONObject managerJson = new JSONObject();
        managerJson.put("id", getManager());
        if (getManager() != null) {
            employeeJson.put("manager", managerJson);
        }
        JSONObject departmentJson = new JSONObject();
        departmentJson.put("id", getDepartment());
        if (getDepartment() != null) {
            departmentJson.put("department", departmentJson);
        }

        return departmentJson.toString();
    }
}
