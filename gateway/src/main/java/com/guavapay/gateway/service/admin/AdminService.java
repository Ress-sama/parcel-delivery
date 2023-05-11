package com.guavapay.gateway.service.admin;

import com.guavapay.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${service.admin.name}", url = "${service.admin.url}",
        configuration = FeignClientConfiguration.class)
public interface AdminService {

    @PostMapping("/v1/auth/login")
    Object login(@RequestBody LoginAdminRequestDto loginAdminRequestDto);

    @GetMapping("/v1/auth/profile")
    ProfileAdminServiceResponseDto verify(@RequestHeader(name = "Authorization") String token);

    @PostMapping("/v1/auth/register")
    void register(@RequestBody RegisterAdminRequestDto registerAdminRequestDto);

}
