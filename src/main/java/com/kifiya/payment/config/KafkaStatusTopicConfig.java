package com.kifiya.payment.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
public class KafkaStatusTopicConfig {

    private static final String TOPIC_SUCCUSS = "cbe-success";

    private static final String TOPIC_FAILLED = "cbe-failled";

    @Bean
    public NewTopic topictele() {
        return TopicBuilder.name(TOPIC_SUCCUSS)
                .partitions(3)
                .replicas(3)
                .build();
    }
    @Bean
    public NewTopic topicTeleIngest() {
        return TopicBuilder.name(TOPIC_FAILLED)
                .partitions(3)
                .replicas(1)
                .build();
    }

}
