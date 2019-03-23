/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.api.constant;

import lombok.Getter;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public enum SwaggerDoc {
    
    MEMBER_CONTROLLER("Member-Controller", "To manage all the endpoints for membership", "Create Member, Read Member, Update Member, Delete Member"),
    MEMBER_CONTROLLER11("Member-Controller", "To manage all the endpoints for membership", "Create Member, Read Member, Update Member, Delete Member");
    
    private final String title;
    
    @Getter
    private final String description;
    
    @Getter
    private final String tag;
    
    SwaggerDoc(String title, String description, String tag) {
        this.title = title;
        this.description = description;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }
    
}
