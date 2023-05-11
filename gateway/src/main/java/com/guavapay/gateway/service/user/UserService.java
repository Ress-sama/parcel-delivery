package com.guavapay.gateway.service.user;

import com.guavapay.gateway.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${service.user.name}", url = "${service.user.url}",
        configuration = FeignClientConfiguration.class)
public interface UserService {

    @PostMapping("/v1/auth/login")
    Object login(@RequestBody LoginUserServiceRequestDto loginUserServiceRequestDto);

    @PostMapping("/v1/auth/register")
    void register(@RequestBody RegisterUserServiceRequestDto registerUserServiceRequestDto);

    @GetMapping("/v1/auth/profile")
    ProfileUserServiceResponseDto verify(@RequestHeader(name = "Authorization") String token);

}
