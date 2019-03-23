/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.api.response;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public abstract class Response {
    /**
     * The response code for a request
     * @return 
     */
    public abstract String getResponseCode();
    
    /**
     * Gets the payload of the response
     * @return 
     */
    public abstract Object getResponseMessage();
}
