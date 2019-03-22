/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service.impl;

import com.flightright.persistence.model.Member;
import com.flightright.persistence.repo.MemberRepo;
import com.flightright.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
public class MemberServiceImpl implements MemberService {
    
    private final MemberRepo memberRepo;
    
    @Autowired
    public MemberServiceImpl(MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public void saveMember(Member member) {
        memberRepo.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepo.findById(id).orElseGet(() -> null);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepo.deleteById(id);
    }
    
}
