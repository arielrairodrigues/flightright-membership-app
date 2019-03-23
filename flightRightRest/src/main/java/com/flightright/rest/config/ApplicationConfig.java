/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.config;

import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperty.class)
@EnableTransactionManagement
public class ApplicationConfig {
    
    @Autowired
    private ApplicationProperty property;
    
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.addUrlMappings("/*");
        
        registration.setMultipartConfig(new MultipartConfigElement("", property.getMaxFileSize(), property.getMaxRequestSize(), 0));
        registration.setAsyncSupported(true);
        return registration;
    }
}
