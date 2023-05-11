package com.guava.deliveryservice.process;

import com.guava.deliveryservice.common.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryProcessFactory {

    private final DeliveryCanceledProcess deliveryCanceledProcess;
    private final DeliveryOnCourierProcess deliveryOnCourierProcess;
    private final DeliveryDeliveredProcess deliveryDeliveredProcess;
    private final DeliveryWaitingCourierProcess deliveryWaitingCourierProcess;

    public BaseDeliveryProcess create(DeliveryStatus status) {
        return switch (status) {
            case WAITING_FOR_COURIER_RESPONSE -> deliveryWaitingCourierProcess;
            case ON_COURIER -> deliveryOnCourierProcess;
            case DELIVERED -> deliveryDeliveredProcess;
            case CANCELED -> deliveryCanceledProcess;
        };
    }

}
