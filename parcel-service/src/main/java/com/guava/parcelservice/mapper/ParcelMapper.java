package com.guava.parcelservice.mapper;

import com.guava.parcelservice.domain.Parcel;
import com.guava.parcelservice.model.ParcelRequestDto;
import com.guava.parcelservice.model.ParcelResponseDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ParcelMapper {

    public Parcel toEntity(ParcelRequestDto parcelRequestDto) {
        if (Objects.isNull(parcelRequestDto)) return null;

        Parcel parcel = new Parcel();
        parcel.setDetails(parcelRequestDto.getDetails());
        parcel.setStartLocation(parcelRequestDto.getStartLocation());
        parcel.setTargetLocation(parcelRequestDto.getTargetLocation());
        parcel.setUserId(parcelRequestDto.getUserId());
        return parcel;
    }

    public ParcelResponseDto toDto(Parcel parcel) {
        if (Objects.isNull(parcel)) return null;

        ParcelResponseDto parcelResponseDto = new ParcelResponseDto();
        parcelResponseDto.setId(parcel.getId());
        parcelResponseDto.setDetails(parcel.getDetails());
        parcelResponseDto.setStatus(parcel.getStatus());
        parcelResponseDto.setUserId(parcel.getUserId());
        parcelResponseDto.setStartLocation(parcel.getStartLocation());
        parcelResponseDto.setTargetLocation(parcel.getTargetLocation());
        return parcelResponseDto;
    }
}
