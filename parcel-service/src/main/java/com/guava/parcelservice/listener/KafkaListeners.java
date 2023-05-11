package com.guava.parcelservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guava.parcelservice.process.deliverystatus.BaseDeliveryStatusProcess;
import com.guava.parcelservice.process.deliverystatus.BaseDeliveryStatusProcessFactory;
import com.guava.parcelservice.process.deliverystatus.DeliveryDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {
    private final BaseDeliveryStatusProcessFactory deliveryStatusProcessFactory;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @KafkaListener(topics = "delivery_status_updated", groupId = "delivery_status_updaters")
    protected void deliveryStatusUpdatedListener(String data) {
        log.info(data);
        DeliveryDto deliveryDto = objectMapper.readValue(data, DeliveryDto.class);
        BaseDeliveryStatusProcess process = deliveryStatusProcessFactory.create(deliveryDto.getStatus());
        process.tryProcess(deliveryDto);
    }

}
