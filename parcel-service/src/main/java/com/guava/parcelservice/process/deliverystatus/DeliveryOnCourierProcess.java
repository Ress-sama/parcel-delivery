package com.guava.parcelservice.process.deliverystatus;

import com.guava.parcelservice.common.ParcelStatus;
import com.guava.parcelservice.domain.Parcel;
import com.guava.parcelservice.error.EntityNotFoundException;
import com.guava.parcelservice.repository.ParcelRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryOnCourierProcess extends BaseDeliveryStatusProcess {

    private final ParcelRepository parcelRepository;

    @Override
    protected void process(DeliveryDto delivery) {
        Parcel parcel = parcelRepository.findById(delivery.getParcelId())
                .orElseThrow(() -> new EntityNotFoundException(Parcel.class, delivery.getParcelId()));
        parcel.setStatus(ParcelStatus.ON_COURIER);
        parcelRepository.save(parcel);
    }

    @Override
    protected void checkProcessIsPossible(DeliveryDto delivery) {
        //WE CAN WRITE CHECK PROCESS FOR BUSINESS LOGIC
    }

}
