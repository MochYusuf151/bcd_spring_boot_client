/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcc40.client.entities;

import lombok.Data;

/**
 *
 * @author Mochamad Yusuf
 */
@Data
public class SelectData {
    public Object id;
    public Object text;

    public SelectData(Object id, Object text) {
        this.id = id;
        this.text = text;
    }
}
