/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence;

import com.flightright.persistence.model.Member;
import com.flightright.persistence.repo.MemberRepo;
import static com.flightright.persistence.util.Util.mockMember;
import javax.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("com.flightright.persistence")
@Slf4j
public class MemberRepoTest {
    
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired 
    private MemberRepo memberRepo;
    
    /**
     * Test case for save member
     * @throws Exception 
     */
    @Test
    public void when_saveMember_thenReturnMember() throws Exception {
        Member member = testEntityManager.persist(mockMember());
        testEntityManager.flush();
        
        assertNotNull(member);
        assertEquals(member.getId(), member.getId());
    }
    
    /**
     * Test NULL values
     * @throws Exception 
     */
    @Test(expected = PersistenceException.class)
    public void when_saveMemberWithNullValues_thenThrowException() throws Exception {
        testEntityManager.persist(new Member());
        testEntityManager.flush();
        fail("Persistence Exception should have been thrown");
    }
    
    /**
     * Test find member by ID
     * @throws Exception 
     */
    @Test
    public void when_findMemberById_thenReturnMember() throws Exception {
        Member member = testEntityManager.persist(mockMember());
        testEntityManager.flush();
        
        assertNotNull(member);
        
        Member getMember = memberRepo.findById(member.getId()).orElseGet(() -> null);
        assertNotNull(getMember);
        assertEquals(getMember.getFirstName(), mockMember().getFirstName());
    }
    
    /**
     * Test case for delete member
     * @throws Exception 
     */
    @Test
    public void when_deleteMember_thenReturnNothing() throws Exception {
        Member member = testEntityManager.persist(mockMember());
        testEntityManager.flush();
        
        assertNotNull(member);
        assertEquals(member.getId(), member.getId());
        
        // delete member
        memberRepo.deleteById(member.getId());
        
        Member getMember = memberRepo.findById(member.getId()).orElseGet(() -> null);
        assertNull(getMember);
    }
    
    /**
     * Clean up the data
     */
    @After
    public void cleanUp() {
        testEntityManager.clear();
    }
}
