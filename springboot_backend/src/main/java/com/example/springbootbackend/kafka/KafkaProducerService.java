package com.example.springbootbackend.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * KafkaProducerService exposes a simple method to publish messages to a topic.
 */
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // PUBLIC_INTERFACE
    public void send(String topic, String message) {
        /** Send a message to the provided Kafka topic. */
        kafkaTemplate.send(topic, message);
    }
}
