package com.guavapay.gateway.controller;

import com.guavapay.gateway.service.admin.AdminService;
import com.guavapay.gateway.service.admin.LoginAdminRequestDto;
import com.guavapay.gateway.service.admin.RegisterAdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/auth/login")
    public Object login(@RequestBody LoginAdminRequestDto requestDto) {
        return adminService.login(requestDto);
    }

    @PostMapping("/auth/register")
    public void register(@RequestBody RegisterAdminRequestDto requestDto) {
        adminService.register(requestDto);
    }

    @PostMapping("/auth/verify")
    public Object verify(HttpServletRequest httpServletRequest) {
        return adminService.verify(httpServletRequest.getHeader("Authorization"));
    }

}
