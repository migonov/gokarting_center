package com.example.gokartingsystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic}")
    private String resultTopic;

    @Bean
    public NewTopic resultTopic() {
        return TopicBuilder.name(resultTopic).build();
    }
}
