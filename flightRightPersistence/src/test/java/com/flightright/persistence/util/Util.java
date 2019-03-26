/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence.util;

import com.flightright.persistence.model.Member;
import java.util.Date;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

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
                                   .setDateOfBirth(new Date())
                                   .setPostalCode("100212")
                                   .setLastName("Megafu").build();
    }
    
    /**
     * Mock the list of members
     * @return 
     */
    public static List<Member> mockMembers() {
        Member member = new Member(1L, "Nonso", "Megafu", new Date(), "100212", "picture.jgp", new Date(), new Date());
        Member memberTwo = new Member(2L, "Nonso2", "Megafu2", new Date(), "100212", "picture2.jgp", new Date(), new Date());
        
        return Stream.of(member, memberTwo).collect(toList());
    }
}
