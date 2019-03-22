/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service.jms;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface ProducerService {
    
    /**
     * This method sends payload to a topic
     * @param topic
     * @param payload 
     */
    void sendToTopic(String topic, String payload);
}
