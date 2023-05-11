package com.guavapay.adminservice.controller;

import com.guavapay.adminservice.model.LoginRequestDto;
import com.guavapay.adminservice.model.ProfileResponseDto;
import com.guavapay.adminservice.model.RefreshTokenRequestDto;
import com.guavapay.adminservice.model.RegisterUserRequestDto;
import com.guavapay.adminservice.model.TokenResponseDto;
import com.guavapay.adminservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return new TokenResponseDto(authenticationService.login(loginRequestDto));
    }

    @PostMapping("/refresh")
    public TokenResponseDto refresh(@RequestBody @Valid RefreshTokenRequestDto requestDto) {
        return new TokenResponseDto(authenticationService.refresh(requestDto));
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterUserRequestDto requestDto) {
        authenticationService.register(requestDto);
    }

    @GetMapping("/profile")
    public ProfileResponseDto getProfile() {
        return authenticationService.getProfile();
    }

}
