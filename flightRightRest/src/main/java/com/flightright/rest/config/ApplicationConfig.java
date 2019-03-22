/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperty.class)
@EnableTransactionManagement
public class ApplicationConfig {
    
}
