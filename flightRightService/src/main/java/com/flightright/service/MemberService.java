/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service;

import com.flightright.persistence.model.Member;
import java.util.List;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface MemberService {
    /**
     * Saves a new member
     * @param member 
     */
    void saveMember(Member member);
    
    /**
     * Finds a new member by ID
     * @param id
     * @return 
     */
    Member findMember(Long id);
    
    /**
     * Deletes an existing member
     * @param id 
     */
    void deleteMember(Long id);
    
    /**
     * Returns all the listed members
     * @return 
     */
    List<Member> findAll();
}
