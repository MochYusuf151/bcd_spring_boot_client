/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import lombok.Data;

/**
 *
 * @author aqira
 */
@Data
public class Job {
    private String jobId,
            jobTitle;
    private int maxSalary,
            minSalary;
}
