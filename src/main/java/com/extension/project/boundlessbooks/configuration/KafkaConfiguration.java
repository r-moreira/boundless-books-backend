package com.extension.project.boundlessbooks.configuration;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile("kafka")
public class KafkaConfiguration {

    private final ApplicationProperties applicationProperties;

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(applicationProperties.getKafka().getTopicName(), 1, (short) 1);
    }
}
