package com.kifiya.payment.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
public class KafkaTopicConfig {

    public static final String TOPIC_UPDATE = "telebir-update";

    public static final String TOPIC_INGEST = "telebir-ingest";

    public static final String TOPIC_UPDATE_CBE = "cbe-update";

    public static final String TOPIC_INGEST_CBE = "cbe-ingest";

    @Bean
    public NewTopic topictele() {
        return TopicBuilder.name(TOPIC_UPDATE)
                .partitions(3)
                .replicas(3)
                .build();
    }
    @Bean
    public NewTopic topicTeleIngest() {
        return TopicBuilder.name(TOPIC_INGEST)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topicteleUpdateCbe() {
        return TopicBuilder.name(TOPIC_UPDATE_CBE)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topicTeleIngestCbe() {
        return TopicBuilder.name(TOPIC_INGEST_CBE)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
