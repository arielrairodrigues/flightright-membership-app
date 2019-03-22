/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@ConfigurationProperties(prefix = "flightright")
@Data
@Validated
public class ApplicationProperty {
    
    /**
     * Swagger base package
     * 
     */
    @NotNull
    private String swaggerBasePackage = "com.flightright.rest.controller";
    
    /**
     * Swagger Document Title
     * 
     */
    @NotNull
    private String swaggerDocTitle = "Flightright Membership Application";
    
    /**
     * Swagger Document Description
     * 
     */
    @NotNull
    private String swaggerDocDescription = "This application manages flightright membership";
    
    /**
     * Swagger Document Version
     * 
     */
    @NotNull
    private String swaggerDocVersion = "1.0.0";
    
    /**
     * The name of the developer
     */
    @NotNull
    private String developerName = "Charles Megafu";
    
    /**
     * The Profile of the developer
     * 
     */
    @NotNull
    private String developerUrl = "https://www.linkedin.com/in/charles-megafu-295a2768/";
    
    /**
     * The email of the developer
     * 
     */
    @NotNull
    private String developerEmail = "noniboycharsy@gmail.com";
    
    /**
     * Active MQ Broker URL
     */
    @NotBlank
    private String activemqBrokerUrl = "vm://localhost";
    
    /**
     * Active MQ Consumer Thread Pool
     * 
     */
    @NotBlank
    private String activemqConsumerThreadPool = "2-100";
    
    /**
     * ActiveMQ Delivery Retrial
     * 
     */
    private int activemqMessageDeliveryRetrial = 1;
    
    /**
     * The Active MQ topic to handle the saving of members
     */
    @NotBlank
    private String activemqSaveMemberTopic = "save-member";
    
    /**
     * The Active MQ topic to handle the update of members
     */
    @NotBlank
    private String activemqUpdateMemberTopic = "update-member";
    
    /**
     * The Active MQ topic to handle the delete of members
     */
    @NotBlank
    private String activemqDeleteMemberTopic = "delete-member";
}
