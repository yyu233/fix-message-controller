package com.beaconfire.project.trading.allocationengine.controller;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beaconfire.project.trading.allocationengine.domain.FixInfoRecordedEvent;
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
        FixInfoRecordedEvent fixInfoRecordedEvent = new FixInfoRecordedEvent();
        fixInfoRecordedEvent.setFixString(message);
        AvroMessageConverter<com.beaconfire.project.trading.allocationengine.domain.FixInfoRecordedEvent> messageConverter = new AvroMessageConverter<>(schemaRegistryUrl);
        EventPublisher<com.beaconfire.project.trading.allocationengine.domain.FixInfoRecordedEvent> avroEventPublisher = new EventPublisher<>(producerFactory, properties, messageConverter);

        MessageWrapper<com.beaconfire.project.trading.allocationengine.domain.FixInfoRecordedEvent> avronMessageWrapper = new MessageWrapper.Builder<com.beaconfire.project.trading.allocationengine.domain.FixInfoRecordedEvent>()
                .topic("eric-c")
                .messageKey("alloc-engine")
                .data(fixInfoRecordedEvent)
                .build();

        avroEventPublisher.sendMessage(avronMessageWrapper);
        return ResponseEntity.ok("Message is sent successfully");
    }
}
