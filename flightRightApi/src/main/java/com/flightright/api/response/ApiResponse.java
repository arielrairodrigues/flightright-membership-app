/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.api.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse extends Response {
    private String responseCode;
    private Object responseMessage;

    @Override
    public Object getResponseMessage() {
        return responseMessage;
    }

    @Override
    public String getResponseCode() {
        return responseCode;
    }
    
}
