package com.guavapay.gateway.controller;

import com.guavapay.gateway.common.PaginationRequest;
import com.guavapay.gateway.model.LocationRequestDto;
import com.guavapay.gateway.model.ParcelRequestDto;
import com.guavapay.gateway.service.admin.AdminService;
import com.guavapay.gateway.service.admin.ProfileAdminServiceResponseDto;
import com.guavapay.gateway.service.parcel.LocationParcelRequestDto;
import com.guavapay.gateway.service.parcel.ParcelService;
import com.guavapay.gateway.service.parcel.ParcelServiceRequestDto;
import com.guavapay.gateway.service.parcel.ParcelStatus;
import com.guavapay.gateway.service.user.ProfileUserServiceResponseDto;
import com.guavapay.gateway.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/v1/parcels")
@Slf4j
public class ParcelController {

    private final AdminService adminService;
    private final ParcelService parcelService;
    private final UserService userService;

    @ApiOperation(value = "Get all parcels",
            notes = "Only admins can see all parcels. You must be login as a ADMIN!")
    @GetMapping
    public Object getPage(PaginationRequest paginationRequest, HttpServletRequest httpServletRequest) {
        log.info("get page");
        var data = adminService.verify(httpServletRequest.getHeader("Authorization"));
        log.info("data",data.getFullName());
        return parcelService.getPage(paginationRequest.getPage(), paginationRequest.getSize());
    }

    @ApiOperation(value = "Create a parcel",
            notes = "Only users can create a parcel. You must be login as a USER!")
    @PostMapping
    public Object create(@RequestBody ParcelRequestDto parcelRequestDto, HttpServletRequest httpServletRequest) {
        ProfileUserServiceResponseDto profileUserServiceResponseDto = userService
                .verify(httpServletRequest.getHeader("Authorization"));

        ParcelServiceRequestDto parcelServiceRequestDto = new ParcelServiceRequestDto();
        parcelServiceRequestDto.setStartLocation(parcelRequestDto.getStartLocation());
        parcelServiceRequestDto.setTargetLocation(parcelRequestDto.getTargetLocation());
        parcelServiceRequestDto.setDetails(parcelRequestDto.getDetails());
        parcelServiceRequestDto.setUserId(profileUserServiceResponseDto.getId());
        return parcelService.create(parcelServiceRequestDto);
    }

    @ApiOperation(value = "Change parcel destination",
            notes = "Only users can change parcel start location. You must be login as a USER!")
    @PutMapping("/{id}/change-start-location")
    public void changeStartLocation(@PathVariable Integer id, @RequestBody LocationRequestDto requestDto,
                                    HttpServletRequest httpServletRequest) {
        ProfileUserServiceResponseDto profileClientResponseDto = userService
                .verify(httpServletRequest.getHeader("Authorization"));

        LocationParcelRequestDto locationClientRequestDto = new LocationParcelRequestDto();
        locationClientRequestDto.setLatitude(requestDto.getLatitude());
        locationClientRequestDto.setLongitude(requestDto.getLongitude());
        locationClientRequestDto.setUserId(profileClientResponseDto.getId());
        parcelService.updateStartLocation(id, locationClientRequestDto);
    }

    @ApiOperation(value = "Change parcel destination",
            notes = "Only users can change parcel target location. You must be login as a USER!")
    @PutMapping("/{id}/change-target-location")
    public void changeTargetLocation(@PathVariable Integer id, @RequestBody LocationRequestDto requestDto,
                                     HttpServletRequest httpServletRequest) {
        ProfileUserServiceResponseDto profileClientResponseDto = userService
                .verify(httpServletRequest.getHeader("Authorization"));

        LocationParcelRequestDto locationClientRequestDto = new LocationParcelRequestDto();
        locationClientRequestDto.setLatitude(requestDto.getLatitude());
        locationClientRequestDto.setLongitude(requestDto.getLongitude());
        locationClientRequestDto.setUserId(profileClientResponseDto.getId());
        parcelService.updateTargetLocation(id, locationClientRequestDto);
    }

    @ApiOperation(value = "Cancel parcel",
            notes = "Only owner users can cancel parcel. You must be login as a USER!")
    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        ProfileUserServiceResponseDto profileClientResponseDto = userService
                .verify(httpServletRequest.getHeader("Authorization"));
        parcelService.cancel(id, profileClientResponseDto.getId());
    }

    @ApiOperation(value = "Change parcel status",
            notes = "Only admins can change parcel status. You must be login as a ADMIN!")
    @PutMapping("/{id}/change-status")
    public void changeStatus(@PathVariable Integer id, @RequestParam ParcelStatus status,
                             HttpServletRequest httpServletRequest) {
        adminService.verify(httpServletRequest.getHeader("Authorization"));
        parcelService.changeStatus(id, status);
    }

    @ApiOperation(value = "Get own parcels",
            notes = "Only users can get own parcels. You must be login as a USER!")
    @GetMapping("/by-user")
    public Object getPageByUser(PaginationRequest paginationRequest,
                                HttpServletRequest httpServletRequest) {
        ProfileUserServiceResponseDto profileUserServiceResponseDto = userService
                .verify(httpServletRequest.getHeader("Authorization"));
        return parcelService.getPageByUserId(profileUserServiceResponseDto.getId(),
                paginationRequest.getPage(), paginationRequest.getSize());
    }

}
