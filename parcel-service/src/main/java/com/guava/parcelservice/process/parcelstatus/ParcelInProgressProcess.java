package com.guava.parcelservice.process.parcelstatus;

import com.guava.parcelservice.common.ParcelStatus;
import com.guava.parcelservice.domain.Parcel;
import com.guava.parcelservice.error.ProcessIsNotPossibleException;
import com.guava.parcelservice.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ParcelInProgressProcess extends BaseParcelProcess {

    private final ParcelRepository parcelRepository;

    @Override
    protected void process(Parcel parcel) {
        parcel.setStatus(ParcelStatus.IN_PROGRESS);
        parcelRepository.save(parcel);
    }

    @Override
    protected void checkProcessIsPossible(Parcel parcel) {
        if (Objects.nonNull(parcel.getStatus()))
            throw new ProcessIsNotPossibleException("Illegal process. orderId : " + parcel.getId() +
                    ". Contact with developer team");
    }
}
