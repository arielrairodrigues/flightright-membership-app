/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service;

import com.flightright.persistence.model.Member;
import com.flightright.persistence.repo.MemberRepo;
import com.flightright.service.impl.MemberServiceImpl;
import static com.flightright.service.util.Util.mockMember;
import static com.flightright.service.util.Util.mockMembers;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
    
    /**
     * Test case to find member by ID
     * @throws Exception 
     */
    @Test
    public void when_findMember_thenReturnMember() throws Exception {
        when(memberRepo.findById(any())).thenReturn(Optional.of(mockMember()));
        // mock service
        Member member = memberService.findMember(1L);
        
        assertNotNull(member);
        assertEquals(member.getFirstName(), mockMember().getFirstName());
        assertEquals(member.getLastName(), mockMember().getLastName());
        assertThat(member.getPicture(), is(mockMember().getPicture()));
        verify(memberRepo, times(1)).findById(any());
    }
    
    /**
     * Test case for delete member
     * @throws Exception 
     */
    @Test
    public void when_deleteMember_thenDoNothing() throws Exception {
        doNothing().when(memberRepo).deleteById(any());
        // make service call
        memberService.deleteMember(1L);
        verify(memberRepo, times(1)).deleteById(any());
    }
    
    /**
     * Test case to get all members
     * @throws Exception 
     */
    @Test
    public void when_findAll_thenReturnList() throws Exception {
        when(memberRepo.findAll()).thenReturn(mockMembers());
        
        // make the service call
        List<Member> members = memberService.findAll();
        
        assertNotNull(members);
        assertFalse(members.isEmpty());
        assertThat(members, containsInAnyOrder( // confirm that each of the elements returned the appropriate expected attributes
                hasProperty("firstName", is("Nonso"))
                ));
        assertThat(members.size(), is(mockMembers().size())); // confirm that the sizes are the same
    }
}
