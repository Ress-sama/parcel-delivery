package com.guavapay.courierservice.process;

import com.guavapay.courierservice.entity.User;
import com.guavapay.courierservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class DeliveryCanceledProcess extends BaseDeliveryStatusProcess {

    private final UserRepository userRepository;

    @Override
    protected void process(DeliveryDto delivery) {
        User courier = userRepository.findById(delivery.getCourierId()).orElseThrow(EntityNotFoundException::new);
        //In this case, the parcel may remain with the courier and may have to go through another process
        //We can write this logic in here
        //but i will change only the courier status.
        courier.setAvailable(false);
        userRepository.save(courier);
    }

    @Override
    protected void checkProcessIsPossible(DeliveryDto delivery) {
        //WE CAN WRITE CHECK PROCESS FOR BUSINESS LOGIC
    }

}
