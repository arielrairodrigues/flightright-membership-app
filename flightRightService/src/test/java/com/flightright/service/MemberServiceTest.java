/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service;

import com.flightright.persistence.model.Member;
import com.flightright.persistence.repo.MemberRepo;
import com.flightright.service.impl.MemberServiceImpl;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class MemberServiceTest {
    
    private MemberRepo memberRepo;
    private MemberService memberService;
    
    @Before
    public void setup() {
        memberRepo = mock(MemberRepo.class);
        memberService = new MemberServiceImpl(memberRepo);
    }
    
    /**
     * Test save a new member
     * @throws Exception 
     */
    @Test
    public void when_saveMember_thenReturnVoid() throws Exception {
        when(memberRepo.save(any())).thenReturn(new Member());
        // make service call
        memberService.saveMember(new Member());
        verify(memberRepo, times(1)).save(any());
    }
}
