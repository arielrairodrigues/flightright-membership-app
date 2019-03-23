/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.api.response;

import java.util.stream.Stream;
import lombok.Getter;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public enum ResponseFormat {
    
    SUCCESSFUL("00", "Successful"),
    PICTURE_ERROR("01", "Unbable to save picture, please try again"),
    MEMBER_DOES_NOT_EXIST("02", "This member does not exist");
    
    @Getter
    private final String status;
    
    @Getter
    private final String message;
    
    ResponseFormat(String status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public static synchronized ResponseFormat findByStatus(String status) {
        return Stream.of(ResponseFormat.values()).filter( i -> i.status.equals(status)).findFirst().orElseGet(() -> null);
    }
}
