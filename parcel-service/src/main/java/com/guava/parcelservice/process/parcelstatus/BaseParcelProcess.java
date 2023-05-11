package com.guava.parcelservice.process.parcelstatus;

import com.guava.parcelservice.domain.Parcel;
import jakarta.transaction.Transactional;

public abstract class BaseParcelProcess {

    @Transactional
    public void tryProcess(Parcel parcel) {
        checkProcessIsPossible(parcel);
        process(parcel);
    }

    protected abstract void process(Parcel parcel);

    protected abstract void checkProcessIsPossible(Parcel parcel);

}
