package com.guavapay.courierservice.service;

import com.guavapay.courierservice.common.SecurityUtil;
import com.guavapay.courierservice.common.TokenProvider;
import com.guavapay.courierservice.entity.User;
import com.guavapay.courierservice.model.LoginRequestDto;
import com.guavapay.courierservice.model.ProfileResponseDto;
import com.guavapay.courierservice.model.RefreshTokenRequestDto;
import com.guavapay.courierservice.model.RegisterUserRequestDto;
import com.guavapay.courierservice.model.UserDetails;
import com.guavapay.courierservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public String login(LoginRequestDto loginRequestDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                loginRequestDto.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

    public void register(RegisterUserRequestDto registerUserRequestDto) {
        User user = new User();
        user.setUsername(registerUserRequestDto.getUsername());
        user.setFullName(registerUserRequestDto.getFullName());
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        userRepository.save(user);
    }

    public String refresh(RefreshTokenRequestDto requestDto) {
        return tokenProvider.createRefreshToken(requestDto.getToken());
    }

    public ProfileResponseDto getProfile() {
        UserDetails userDetails = securityUtil.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        profileResponseDto.setUsername(userDetails.getUsername());
        profileResponseDto.setFullName(userDetails.getFullName());
        profileResponseDto.setId(user.getId());
        return profileResponseDto;
    }

}
