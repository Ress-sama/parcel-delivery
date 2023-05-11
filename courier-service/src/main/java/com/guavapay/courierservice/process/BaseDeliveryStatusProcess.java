package com.guavapay.courierservice.process;

import org.springframework.transaction.annotation.Transactional;

public abstract class BaseDeliveryStatusProcess {

    @Transactional
    public void tryProcess(DeliveryDto delivery) {
        checkProcessIsPossible(delivery);
        process(delivery);
    }

    protected abstract void process(DeliveryDto delivery);

    protected abstract void checkProcessIsPossible(DeliveryDto delivery);

}
