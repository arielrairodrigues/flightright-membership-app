/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.jms;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface MemberConsumerService {
    
    /**
     * Topic to save a new member
     * @param payload 
     */
    void saveMember(String payload);
    
    /**
     * Topic to update member
     * @param payload 
     */
    void updateMember(String payload);
    
    /**
     * Topic to delete member
     * @param payload 
     */
    void deleteMember(String payload);
}
