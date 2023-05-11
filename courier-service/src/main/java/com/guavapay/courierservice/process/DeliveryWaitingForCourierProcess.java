package com.guavapay.courierservice.process;

import com.guavapay.courierservice.entity.User;
import com.guavapay.courierservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class DeliveryWaitingForCourierProcess extends BaseDeliveryStatusProcess{

    private final UserRepository userRepository;

    @Override
    protected void process(DeliveryDto delivery) {
        User courier = userRepository.findById(delivery.getCourierId()).orElseThrow(EntityNotFoundException::new);
        //WE CAN NOTIFY COURIER FOR DELIVERY
        userRepository.save(courier);
    }

    @Override
    protected void checkProcessIsPossible(DeliveryDto delivery) {
        //WE CAN WRITE CHECK PROCESS FOR BUSINESS LOGIC
    }

}
