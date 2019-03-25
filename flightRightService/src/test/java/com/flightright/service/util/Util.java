/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service.util;

import com.flightright.persistence.model.Member;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public final class Util {
    
    private Util() {}
    
    /**
     * Mock Member
     * @return 
     */
    public static Member mockMember() {
        return new Member.Builder().setFirstName("Nonso")
                                   .setPicture("cert.jpg")
                                   .setLastName("Megafu").build();
    }
    
    /**
     * Mock the list of members
     * @return 
     */
    public static List<Member> mockMembers() {
        Member member = new Member(1L, "Nonso", "Megafu", null, "100212", "picture.jgp", null, null);
        List<Member> members = new ArrayList<>();
        members.add(member);
        return members;
    }
}
