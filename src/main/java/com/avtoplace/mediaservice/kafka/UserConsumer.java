package com.avtoplace.mediaservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @KafkaListener(topics = "user-created-topic", groupId = "media-group")
    public void consume(String event) {
        System.out.println("MESSAGE FROM KAFKA = " + event);
    }
}
