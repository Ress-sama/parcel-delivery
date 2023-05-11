package com.guavapay.courierservice.controller;

import com.guavapay.courierservice.entity.Location;
import com.guavapay.courierservice.model.UserResponseDto;
import com.guavapay.courierservice.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @GetMapping("/{id}/last-location")
    public Location getLastLocationByUserId(@PathVariable Integer id) {
        return courierService.getLastLocation(id);
    }

    @GetMapping
    public Page<UserResponseDto> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        return courierService.getAll(page, size);
    }

}
