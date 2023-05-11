package com.guava.parcelservice.controller;

import com.guava.parcelservice.common.PaginationRequest;
import com.guava.parcelservice.common.ParcelStatus;
import com.guava.parcelservice.domain.Location;
import com.guava.parcelservice.model.LocationRequestDto;
import com.guava.parcelservice.model.ParcelRequestDto;
import com.guava.parcelservice.model.ParcelResponseDto;
import com.guava.parcelservice.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping
    public Page<ParcelResponseDto> getAll(PaginationRequest paginationRequest) {
        return parcelService.getAll(paginationRequest);
    }

    @GetMapping("/{id}")
    public ParcelResponseDto getById(@PathVariable Integer id) {
        return parcelService.getById(id);
    }

    @GetMapping("/users/{userId}/parcels")
    public Page<ParcelResponseDto> getAllByUserId(@PathVariable Integer userId, PaginationRequest paginationRequest) {
        return parcelService.getAllByUserId(userId, paginationRequest);
    }

    @PostMapping
    public Integer create(@RequestBody ParcelRequestDto parcelRequestDto) {
        return parcelService.create(parcelRequestDto);
    }

    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Integer id, ParcelStatus parcelStatus) {
        parcelService.changeStatus(id, parcelStatus);
    }

    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id, @RequestParam Integer userId) {
        parcelService.cancel(id, userId);
    }

    @PutMapping("/{id}/update-start-location")
    public void updateStartLocation(@PathVariable Integer id, @RequestBody LocationRequestDto locationRequest) {
        parcelService.changeStartLocation(id, locationRequest);
    }

    @PutMapping("/{id}/update-target-location")
    public void updateTargetLocation(@PathVariable Integer id, @RequestBody LocationRequestDto locationRequest) {
        parcelService.changeTargetLocation(id, locationRequest);
    }

}
