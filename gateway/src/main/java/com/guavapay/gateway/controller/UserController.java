package com.guavapay.gateway.controller;

import com.guavapay.gateway.service.user.LoginUserServiceRequestDto;
import com.guavapay.gateway.service.user.RegisterUserServiceRequestDto;
import com.guavapay.gateway.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public Object login(@RequestBody LoginUserServiceRequestDto requestDto) {
        return userService.login(requestDto);
    }

    @PostMapping("/auth/register")
    public void register(@RequestBody RegisterUserServiceRequestDto requestDto) {
        userService.register(requestDto);
    }

    @PostMapping("/auth/verify")
    public Object verify(HttpServletRequest httpServletRequest) {
        return userService.verify(httpServletRequest.getHeader("Authorization"));
    }

}
