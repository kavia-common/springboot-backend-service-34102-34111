package com.example.springbootbackend.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaConfig wires up producer & admin beans.
 * We use String key/value for simplicity.
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.test-topic:test-topic}")
    private String testTopic;

    // PUBLIC_INTERFACE
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        /** Provide Kafka ProducerFactory using String key/value serializers. */
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    // PUBLIC_INTERFACE
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf) {
        /** KafkaTemplate to send messages easily. */
        return new KafkaTemplate<>(pf);
    }

    // PUBLIC_INTERFACE
    @Bean
    public KafkaAdmin kafkaAdmin() {
        /** KafkaAdmin allows creation of topics via configuration. */
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    // PUBLIC_INTERFACE
    @Bean
    public NewTopic demoTopic() {
        /** Ensure the demo topic exists (idempotent on startup). */
        return TopicBuilder.name(testTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
