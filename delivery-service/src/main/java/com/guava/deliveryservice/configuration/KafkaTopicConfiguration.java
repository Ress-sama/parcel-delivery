package com.guava.deliveryservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    public NewTopic createDeliveryTopic() {
        return TopicBuilder.name("delivery-created").build();
    }

}
