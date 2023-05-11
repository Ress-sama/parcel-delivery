package com.guavapay.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.gateway.common.PaginationRequest;
import com.guavapay.gateway.model.LocationRequestDto;
import com.guavapay.gateway.service.admin.AdminService;
import com.guavapay.gateway.service.courier.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/couriers")
public class CourierController {

    private final CourierService courierService;
    private final AdminService adminService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/auth/login")
    public Object login(@RequestBody LoginCourierRequestDto requestDto) {
        return courierService.login(requestDto);
    }

    @PostMapping("/auth/register")
    public void register(@RequestBody RegisterCourierRequestDto requestDto, HttpServletRequest httpServletRequest) {
        adminService.verify(httpServletRequest.getHeader("Authorization"));
        courierService.register(requestDto);
    }

    @PostMapping("/auth/verify")
    public Object verify(HttpServletRequest httpServletRequest) {
        return courierService.verify(httpServletRequest.getHeader("Authorization"));
    }

    @ApiOperation(value = "Update location with longitude and latitude",
            notes = "Courier can update self location by this. You must be login as a COURIER!")
    @SneakyThrows
    @PutMapping("/location")
    public void updateLocation(@RequestBody LocationRequestDto requestDto,
                               HttpServletRequest httpServletRequest) {
        ProfileCourierResponseDto profileClientResponseDto = courierService
                .verify(httpServletRequest.getHeader("Authorization"));
        UpdateLocationCourierRequestDto updateLocationClientRequestDto = new UpdateLocationCourierRequestDto();
        updateLocationClientRequestDto.setCourierId(profileClientResponseDto.getId());
        updateLocationClientRequestDto.setLongitude(requestDto.getLongitude());
        updateLocationClientRequestDto.setLatitude(requestDto.getLatitude());
        kafkaTemplate.send("update-location", objectMapper.writeValueAsString(updateLocationClientRequestDto));
    }

    @ApiOperation(value = "Get couriers last location by courier id",
            notes = "Only admins can track delivery parcels by courier location. You must be login as a ADMIN!")
    @GetMapping("/{id}/last-location")
    public Object getLastLocationByCourierId(@PathVariable Integer id,
                                             HttpServletRequest httpServletRequest) {
        adminService.verify(httpServletRequest.getHeader("Authorization"));
        return courierService.getLastLocationByUserId(id);
    }

    @ApiOperation(value = "Get all couriers with their available status",
            notes = "Only admins can see all couriers. You must be login as a ADMIN!")
    @GetMapping
    public Object getAll(PaginationRequest paginationRequest,
                         HttpServletRequest httpServletRequest) {
        adminService.verify(httpServletRequest.getHeader("Authorization"));
        return courierService.getAll(paginationRequest.getPage(), paginationRequest.getSize());
    }

}
