package com.yyz.fixmessagecontroller.controller;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beaconfire.project.trading.base.kafkautils.config.KafkaConfigProperties;
import com.beaconfire.project.trading.base.kafkautils.message.MessageWrapper;
import com.beaconfire.project.trading.base.kafkautils.message.converter.impl.AvroMessageConverter;
import com.beaconfire.project.trading.base.kafkautils.producer.CustomKafkaProducerFactory;
import com.beaconfire.project.trading.base.kafkautils.producer.EventPublisher;

@RestController
@RequestMapping("/api")
public class MainController {
    @Autowired
    private CustomKafkaProducerFactory<String, byte[]> producerFactory;

    @Autowired
    private KafkaConfigProperties properties;

    @PostMapping("/publish")
    public ResponseEntity<String> publishFIXMessage(String message) {
        String schemaRegistryUrl = properties.getSchemaRegistryUrl();
        AvroMessageConverter<T> messageConverter = new AvroMessageConverter<>(schemaRegistryUrl);
        EventPublisher<T> avroEventPublisher = new EventPublisher<>(producerFactory, properties, messageConverter);

        MessageWrapper<T> avronMessageWrapper = new MessageWrapper.Builder<T>()
                .topic("eric-c")
                .messageKey()
                .data(message)
                .requestCorrelationId()
                .userId()
                .build();

        avroEventPublisher.sendMessage(avronMessageWrapper);
        return ResponseEntity.ok("Message is sent successfully");
    }
}
