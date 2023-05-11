package com.guava.deliveryservice.controller;

import com.guava.deliveryservice.common.DeliveryStatus;
import com.guava.deliveryservice.common.PaginationRequest;
import com.guava.deliveryservice.model.DeliveryRequestDto;
import com.guava.deliveryservice.model.DeliveryResponseDto;
import com.guava.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public Page<DeliveryResponseDto> getAll(PaginationRequest paginationRequest) {
        return deliveryService.getAll(paginationRequest);
    }

    @GetMapping("/{id}")
    public DeliveryResponseDto getById(@PathVariable Integer id) {
        return deliveryService.getById(id);
    }

    @GetMapping("/couriers/{courierId}/deliveries")
    public Page<DeliveryResponseDto> getAllByCourierId(@PathVariable Integer courierId,
                                                       PaginationRequest paginationRequest) {
        return deliveryService.getPageByCourierId(courierId, paginationRequest);
    }

    @PostMapping
    public Integer create(@RequestBody DeliveryRequestDto deliveryRequestDto) {
        return deliveryService.create(deliveryRequestDto);
    }

    @PutMapping("/{id}/change-status")
    public void updateStatus(@PathVariable Integer id, @RequestParam DeliveryStatus status) {
        deliveryService.updateStatus(id, status);
    }

    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id) {
        deliveryService.updateStatus(id, DeliveryStatus.CANCELED);
    }

}
