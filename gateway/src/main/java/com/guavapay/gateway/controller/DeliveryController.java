package com.guavapay.gateway.controller;

import com.guavapay.gateway.common.PaginationRequest;
import com.guavapay.gateway.service.admin.AdminService;
import com.guavapay.gateway.service.courier.CourierService;
import com.guavapay.gateway.service.courier.ProfileCourierResponseDto;
import com.guavapay.gateway.service.delivery.DeliveryServiceRequestDto;
import com.guavapay.gateway.service.delivery.DeliveryService;
import com.guavapay.gateway.service.delivery.DeliveryStatus;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/deliveries")
public class DeliveryController {

    private final AdminService adminService;
    private final DeliveryService deliveryService;
    private final CourierService courierService;

    @ApiOperation(value = "Create delivery for parcel",
            notes = "Only admins can create delivery for parcel. You must be login as a ADMIN!")
    @PostMapping
    public Object create(@RequestBody DeliveryServiceRequestDto requestDto, HttpServletRequest httpServletRequest) {
        adminService.verify(httpServletRequest.getHeader("Authorization"));
        return deliveryService.create(requestDto);
    }

    @ApiOperation(value = "Get all own deliveries by courier",
            notes = "Only couriers can get own deliveries. You must be login as a COURIER!")
    @GetMapping("/by-courier")
    public Object getDeliveriesByCourier(PaginationRequest paginationRequest,
                                         HttpServletRequest httpServletRequest) {
        ProfileCourierResponseDto profileClientResponseDto = courierService
                .verify(httpServletRequest.getHeader("Authorization"));
        return deliveryService.getDeliveriesByCourierId(profileClientResponseDto.getId(),
                paginationRequest.getPage(), paginationRequest.getSize());
    }

    @ApiOperation(value = "Get deliveries",
            notes = "Only admins can get all deliveries. You must be login as a ADMIN!")
    @GetMapping
    public Object getDeliveries(PaginationRequest paginationRequest,
                                HttpServletRequest httpServletRequest) {
        adminService.verify(httpServletRequest.getHeader("Authorization"));
        return deliveryService.getAll(paginationRequest.getPage(), paginationRequest.getSize());
    }

    @ApiOperation(value = "Change delivery status by id and delivery status",
            notes = "Only couriers can change delivery status to ON_COURIER or DELIVERED. You must be login as a COURIER!")
    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Integer id, @RequestParam DeliveryStatus status,
                             HttpServletRequest httpServletRequest) {
        courierService.verify(httpServletRequest.getHeader("Authorization"));
        deliveryService.changeStatus(id, status);
    }

    @ApiOperation(value = "Cancel delivery",
            notes = "Only admins can cancel delivery. You must be login as a ADMIN!")
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id,
                       HttpServletRequest httpServletRequest) {
        adminService.verify(httpServletRequest.getHeader("Authorization"));
        deliveryService.cancel(id);
    }

}
