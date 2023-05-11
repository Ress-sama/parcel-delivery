package com.guavapay.gateway.service.parcel;

import com.guavapay.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${service.parcel.name}", url = "${service.parcel.url}",
        configuration = FeignClientConfiguration.class)
public interface ParcelService {

    @PostMapping("/v1/parcels")
    Object create(@RequestBody ParcelServiceRequestDto requestDto);

    @GetMapping("/v1/parcels")
    Object getPage(@RequestParam int page, @RequestParam int size);

    @GetMapping("/v1/parcels/{id}")
    Object getById(@PathVariable Integer id);

    @PutMapping("/v1/parcels/{id}/update-target-location")
    void updateTargetLocation(@PathVariable Integer id, @RequestBody LocationParcelRequestDto locationRequest);

    @PutMapping("/v1/parcels/{id}/update-start-location")
    void updateStartLocation(@PathVariable Integer id, @RequestBody LocationParcelRequestDto locationRequest);

    @PutMapping("/v1/parcels/{id}/change-status")
    void changeStatus(@PathVariable Integer id, @RequestParam ParcelStatus status);

    @PutMapping("/v1/parcels/{id}/cancel")
    void cancel(@PathVariable Integer id, @RequestParam Integer userId);

    @GetMapping("/v1/parcels/users/{userId}/parcels")
    Object getPageByUserId(@PathVariable Integer userId, @RequestParam int page, @RequestParam int size);

}
