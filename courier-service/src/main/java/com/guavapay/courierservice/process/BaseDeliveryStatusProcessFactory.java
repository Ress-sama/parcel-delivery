package com.guavapay.courierservice.process;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BaseDeliveryStatusProcessFactory {

    private final DeliveryCanceledProcess deliveryCanceledProcess;
    private final DeliveryOnCourierProcess deliveryOnCourierProcess;
    private final DeliveryWaitingForCourierProcess deliveryWaitingForCourierProcess;
    private final DeliveryDeliveredProcess deliveryDeliveredProcess;

    public BaseDeliveryStatusProcess create(DeliveryStatus status) {

        return switch (status) {
            case WAITING_FOR_COURIER_RESPONSE -> deliveryWaitingForCourierProcess;
            case ON_COURIER -> deliveryOnCourierProcess;
            case DELIVERED -> deliveryDeliveredProcess;
            case CANCELED -> deliveryCanceledProcess;
        };

    }

}
