/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@MappedSuperclass
public class TimestampMapper {
    
    @Column(name = "createdAt", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @PrePersist
    public void dateUpdate() {
        if (null == updatedAt)
            this.updatedAt = new Timestamp(System.currentTimeMillis());
        if (null == createdAt)
            this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
