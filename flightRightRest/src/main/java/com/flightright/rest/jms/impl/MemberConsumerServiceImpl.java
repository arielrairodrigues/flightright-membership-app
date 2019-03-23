/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.jms.impl;

import com.flightright.persistence.model.Member;
import com.flightright.rest.config.ApplicationProperty;
import com.flightright.rest.jms.MemberConsumerService;
import static com.flightright.rest.util.Util.convertStringToObject;
import com.flightright.service.FileProcessorService;
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
    private final FileProcessorService fileProcessorService;
    private final ApplicationProperty property;
    
    @Autowired
    public MemberConsumerServiceImpl(MemberService memberService, 
                                     FileProcessorService fileProcessorService,
                                     ApplicationProperty property) {
        this.memberService = memberService;
        this.fileProcessorService = fileProcessorService;
        this.property = property;
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
        // delete the picture associated with this member
        String file = new StringBuilder(property.getPicturesFolder()).append("/").append(member.getPicture()).toString();
        fileProcessorService.deletePicture(file);
        memberService.deleteMember(member.getId());
    }
    
}
