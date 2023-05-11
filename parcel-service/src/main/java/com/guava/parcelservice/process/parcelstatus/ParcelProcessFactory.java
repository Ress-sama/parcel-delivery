package com.guava.parcelservice.process.parcelstatus;

import com.guava.parcelservice.common.ParcelStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParcelProcessFactory {

    private final ParcelInProgressProcess parcelInProgressProcess;
    private final ParcelOnCourierProcess parcelOnCourierProcess;
    private final ParcelCanceledProcess parcelCanceledProcess;
    private final ParcelWaitingForCourierProcess parcelWaitingForCourierProcess;

    public BaseParcelProcess create(ParcelStatus status) {
        return switch (status) {
            case IN_PROGRESS -> parcelInProgressProcess;
            case WAITING_FOR_COURIER -> parcelWaitingForCourierProcess;
            case ON_COURIER -> parcelOnCourierProcess;
            case CANCELED -> parcelCanceledProcess;
            case DELIVERED -> null;
        };
    }

}
