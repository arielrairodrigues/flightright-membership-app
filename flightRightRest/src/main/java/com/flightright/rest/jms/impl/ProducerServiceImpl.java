/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.jms.impl;

import com.flightright.rest.jms.ProducerService;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
public class ProducerServiceImpl implements ProducerService {
    
    private final JmsTemplate jmsTemplate;
    
    @Value("${flightright.activemq-message-delivery-retrial}")
    private int messageDeliveryRetrial;
    
    public ProducerServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendToTopic(String topic, String payload) {
        jmsTemplate.send(topic, (Session session) -> {
            TextMessage textMessage = session.createTextMessage(payload);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, messageDeliveryRetrial * 60 * 1000); // failed message delivery will repeat within the specified minutes configured
            return textMessage;
        });
    }
    
}
