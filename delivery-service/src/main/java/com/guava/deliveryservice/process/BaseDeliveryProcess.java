package com.guava.deliveryservice.process;

import com.guava.deliveryservice.domain.Delivery;

public abstract class BaseDeliveryProcess {

    public void tryProcess(Delivery delivery) {
        checkProcessIsPossible(delivery);
        process(delivery);
    }

    protected abstract void process(Delivery delivery);

    protected abstract void checkProcessIsPossible(Delivery delivery);

}
