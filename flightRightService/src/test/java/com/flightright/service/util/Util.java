/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service.util;

import com.flightright.persistence.model.Member;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public final class Util {
    
    private Util() {}
    
    public static Member mockMember() {
        return new Member.Builder().setFirstName("Nonso")
                                   .setPicture("cert.jpg")
                                   .setLastName("Megafu").build();
    }
}
