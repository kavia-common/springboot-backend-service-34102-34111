package com.example.springbootbackend.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * KafkaConsumerListener listens to messages on the configured test topic.
 * Logs the received messages for demonstration.
 */
@Component
public class KafkaConsumerListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Value("${app.kafka.test-topic:test-topic}")
    private String testTopic;

    // PUBLIC_INTERFACE
    @KafkaListener(topics = "${app.kafka.test-topic:test-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(String message) {
        /**
         * Receives messages published to the 'test-topic' (or configured topic).
         * For real applications, add your processing logic here.
         */
        log.info("KafkaConsumerListener received message on {}: {}", testTopic, message);
    }
}
