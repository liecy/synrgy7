package com.rahmi.binfood.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "binfood_topic", groupId = "group_id")
    public void consumeMessage(Object message) {
        System.out.println("Consumed message: " + message);
    }
}