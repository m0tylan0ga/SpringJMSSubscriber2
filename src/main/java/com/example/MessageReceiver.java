package com.example;

import com.example.models.Subscriber2;
import com.example.models.SubscriberDao;
import com.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;

@Component
public class MessageReceiver {
    
    @Autowired
    private SubscriberDao subscriberDao;

    static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);

    private static final String ORDER_QUEUE = "order-topic";

    @Transactional(rollbackFor = RuntimeException.class)
    @JmsListener(destination = ORDER_QUEUE, containerFactory = "jmsListenerContainerFactory", selector = "topic_type IN ('BOOKS', 'FOOD', 'SPORT')")
    public void receiveMessage(final Message<User> message) throws JMSException {
        MessageHeaders headers = message.getHeaders();
        log.info("Application : headers received : {}", headers);
        User response = message.getPayload();
        Subscriber2 subscriber1 = new Subscriber2();
        subscriber1.setUser(response);
        subscriberDao.create(subscriber1);
        log.info("Application : response received : {}", response);
    }
}
