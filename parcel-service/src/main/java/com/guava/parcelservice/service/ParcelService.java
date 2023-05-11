package com.guava.parcelservice.service;

import com.guava.parcelservice.common.PaginationRequest;
import com.guava.parcelservice.common.ParcelStatus;
import com.guava.parcelservice.domain.Location;
import com.guava.parcelservice.domain.Parcel;
import com.guava.parcelservice.error.EntityNotFoundException;
import com.guava.parcelservice.error.ProcessIsNotPossibleException;
import com.guava.parcelservice.mapper.ParcelMapper;
import com.guava.parcelservice.model.LocationRequestDto;
import com.guava.parcelservice.model.ParcelRequestDto;
import com.guava.parcelservice.model.ParcelResponseDto;
import com.guava.parcelservice.process.parcelstatus.BaseParcelProcess;
import com.guava.parcelservice.process.parcelstatus.ParcelProcessFactory;
import com.guava.parcelservice.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelMapper parcelMapper;
    private final ParcelRepository parcelRepository;
    private final ParcelProcessFactory parcelProcessFactory;

    public Page<ParcelResponseDto> getAll(PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<Parcel> deliveries = parcelRepository.findAll(pageable);
        return deliveries.map(parcelMapper::toDto);
    }

    public ParcelResponseDto getById(Integer id) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parcel.class, id));
        return parcelMapper.toDto(parcel);
    }

    public Page<ParcelResponseDto> getAllByUserId(Integer userId, PaginationRequest paginationRequest) {
        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        Page<Parcel> deliveries = parcelRepository.findAllByUserId(pageable, userId);
        return deliveries.map(parcelMapper::toDto);
    }

    @Transactional
    public Integer create(ParcelRequestDto parcelRequestDto) {
        Parcel parcel = parcelMapper.toEntity(parcelRequestDto);
        BaseParcelProcess process = parcelProcessFactory.create(ParcelStatus.IN_PROGRESS);
        process.tryProcess(parcel);
        parcel = parcelRepository.save(parcel);
        return parcel.getId();
    }

    public void changeStatus(Integer id, ParcelStatus parcelStatus) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parcel.class, id));
        BaseParcelProcess process = parcelProcessFactory.create(parcelStatus);
        process.tryProcess(parcel);
    }

    public void changeStartLocation(Integer id, LocationRequestDto locationRequest) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parcel.class, id));
        validateLocationChange(parcel, locationRequest);
        Location location = new Location();
        location.setLatitude(locationRequest.getLatitude());
        location.setLongitude(locationRequest.getLongitude());
        parcel.setStartLocation(location);
        parcelRepository.save(parcel);
    }

    public void changeTargetLocation(Integer id, LocationRequestDto locationRequest) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parcel.class, id));
        validateLocationChange(parcel, locationRequest);
        Location location = new Location();
        location.setLatitude(locationRequest.getLatitude());
        location.setLongitude(locationRequest.getLongitude());
        parcel.setTargetLocation(location);
        parcelRepository.save(parcel);
    }

    private void validateLocationChange(Parcel parcel, LocationRequestDto requestDto) {
        ParcelStatus parcelStatus = parcel.getStatus();
        if (!ParcelStatus.IN_PROGRESS.equals(parcelStatus)) {
            throw new ProcessIsNotPossibleException("Cannot parcel location on " + parcelStatus + " state");
        }
        if (!parcel.getUserId().equals(requestDto.getUserId())) {
            throw new ProcessIsNotPossibleException("Owner can change location of delivery");
        }
    }

    @Transactional
    public void cancel(Integer id, Integer userId) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Parcel.class, id));
        if (!parcel.getUserId().equals(userId)) {
            throw new ProcessIsNotPossibleException("Owner can cancel delivery");
        }
        BaseParcelProcess process = parcelProcessFactory.create(ParcelStatus.CANCELED);
        process.tryProcess(parcel);
    }

}
