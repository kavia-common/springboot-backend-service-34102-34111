package com.example.springbootbackend.kafka;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * KafkaDemoController exposes a simple REST endpoint to send Kafka messages.
 */
@RestController
@RequestMapping("/api/kafka")
@Tag(name = "Kafka Demo", description = "Endpoints for testing Kafka producer/consumer")
public class KafkaDemoController {

    private final KafkaProducerService producerService;

    @Value("${app.kafka.test-topic:test-topic}")
    private String testTopic;

    public KafkaDemoController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    // PUBLIC_INTERFACE
    @PostMapping("/send")
    @Operation(
            summary = "Send a message to Kafka",
            description = "Publishes the provided 'message' to the configured test topic. "
                    + "Topic can be configured via 'app.kafka.test-topic'."
    )
    public ResponseEntity<String> send(@RequestParam("message") String message,
                                       @RequestParam(value = "topic", required = false) String topic) {
        /** Sends a message to Kafka using the KafkaProducerService. */
        String targetTopic = (topic == null || topic.isBlank()) ? testTopic : topic;
        producerService.send(targetTopic, message);
        return ResponseEntity.ok("Sent to topic '" + targetTopic + "': " + message);
    }
}
