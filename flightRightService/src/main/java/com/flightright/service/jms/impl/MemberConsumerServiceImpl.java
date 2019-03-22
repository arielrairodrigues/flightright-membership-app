/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service.jms.impl;

import com.flightright.service.jms.MemberConsumerService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
public class MemberConsumerServiceImpl implements MemberConsumerService {

    @Override
    @JmsListener(destination = "${flightright.activemq-save-member-topic}", containerFactory = "containerFactory")
    public void saveMember(String payload) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @JmsListener(destination = "${flightright.activemq-update-member-topic}", containerFactory = "containerFactory")
    public void updateMember(String payload) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @JmsListener(destination = "${flightright.activemq-delete-member-topic}", containerFactory = "containerFactory")
    public void deleteMember(String payload) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
