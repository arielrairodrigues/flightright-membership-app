/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Entity
@Table(name = "members", indexes = {
    @Index(columnList = "firstName", name = "idxMemberFirstName"),
    @Index(columnList = "lastName", name = "idxMemberLastName"),
    @Index(columnList = "postalCode", name = "idxMemberPostalCode")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member extends TimestampMapper implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(length = 100)
    private String firstName;
    
    @Column(length = 100)
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @Column(length = 6)
    private String postalCode;
    
    @Column(length = 200)
    private String picture;
    
    public Member(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.postalCode = builder.postalCode;
        this.picture = builder.picture;
    }
    
    public static class Builder {
        private String firstName; 
        private String lastName;
        private Date dateOfBirth;
        private String postalCode;
        private String picture;

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setDateOfBirth(Date dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setPicture(String picture) {
            this.picture = picture;
            return this;
        }
        
        public Member build() {
            return new Member(this);
        }
    }
}
