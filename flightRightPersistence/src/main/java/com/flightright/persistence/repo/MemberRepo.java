/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence.repo;

import com.flightright.persistence.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface MemberRepo extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    
}
