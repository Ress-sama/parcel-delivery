package com.guava.parcelservice.process.parcelstatus;

import com.guava.parcelservice.common.ParcelStatus;
import com.guava.parcelservice.domain.Parcel;
import com.guava.parcelservice.error.ProcessIsNotPossibleException;
import com.guava.parcelservice.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParcelCanceledProcess extends BaseParcelProcess {

    private final ParcelRepository parcelRepository;

    @Override
    protected void process(Parcel parcel) {
        parcel.setStatus(ParcelStatus.CANCELED);
        parcelRepository.save(parcel);
    }

    @Override
    protected void checkProcessIsPossible(Parcel parcel) {
        if (!ParcelStatus.IN_PROGRESS.equals(parcel.getStatus())) {
            throw new ProcessIsNotPossibleException("Illegal process. orderId : " + parcel.getId() +
                    ". Contact with developer team");
        }
    }

}
