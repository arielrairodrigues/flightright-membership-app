/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence;

import com.flightright.persistence.model.Member;
import static com.flightright.persistence.util.Util.mockMember;
import javax.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    
    /**
     * Test case for save member
     * @throws Exception 
     */
    @Test
    public void when_saveMember_thenReturnMember() throws Exception {
        Member member = testEntityManager.persist(mockMember());
        assertNotNull(member);
        assertEquals(member.getId(), 1L);
    }
}
