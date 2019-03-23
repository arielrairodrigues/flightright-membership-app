/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.jms.impl;

import com.flightright.persistence.model.Member;
import com.flightright.rest.jms.MemberConsumerService;
import static com.flightright.rest.util.Util.convertStringToObject;
import com.flightright.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
public class MemberConsumerServiceImpl implements MemberConsumerService {
    
    private final MemberService memberService;
    
    @Autowired
    public MemberConsumerServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    @JmsListener(destination = "${flightright.activemq-save-member-topic}", containerFactory = "containerFactory")
    public void saveMember(String payload) {
        Member member = convertStringToObject(payload, Member.class);
        memberService.saveMember(member);
    }

    @Override
    @JmsListener(destination = "${flightright.activemq-update-member-topic}", containerFactory = "containerFactory")
    public void updateMember(String payload) {
        Member member = convertStringToObject(payload, Member.class);
        memberService.saveMember(member);
    }

    @Override
    @JmsListener(destination = "${flightright.activemq-delete-member-topic}", containerFactory = "containerFactory")
    public void deleteMember(String payload) {
        Member member = convertStringToObject(payload, Member.class);
        memberService.deleteMember(member.getId());
    }
    
}
