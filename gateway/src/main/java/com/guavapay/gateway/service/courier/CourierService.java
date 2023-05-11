package com.guavapay.gateway.service.courier;

import com.guavapay.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${service.courier.name}", url = "${service.courier.url}",
        configuration = FeignClientConfiguration.class)
public interface CourierService {

    @PostMapping("/v1/auth/login")
    Object login(@RequestBody LoginCourierRequestDto loginClientRequestDto);

    @GetMapping("/v1/auth/profile")
    ProfileCourierResponseDto verify(@RequestHeader(name = "Authorization") String token);

    @PostMapping("/v1/auth/register")
    void register(@RequestBody RegisterCourierRequestDto registerCourierRequestDto);

    @GetMapping("/v1/couriers/{id}/last-location")
    Object getLastLocationByUserId(@PathVariable Integer id);

    @GetMapping("/v1/couriers")
    Object getAll(@RequestParam Integer page, @RequestParam Integer size);

}
