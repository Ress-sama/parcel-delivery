package com.guava.deliveryservice.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guava.deliveryservice.common.DeliveryStatus;
import com.guava.deliveryservice.domain.Delivery;
import com.guava.deliveryservice.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryCanceledProcess extends BaseDeliveryProcess {

    private final DeliveryRepository deliveryRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @SneakyThrows
    protected void process(Delivery delivery) {
        delivery.setStatus(DeliveryStatus.CANCELED);
        deliveryRepository.save(delivery);
        kafkaTemplate.send("delivery_status_updated", objectMapper.writeValueAsString(delivery));
    }

    @Override
    protected void checkProcessIsPossible(Delivery delivery) {
    }

}
