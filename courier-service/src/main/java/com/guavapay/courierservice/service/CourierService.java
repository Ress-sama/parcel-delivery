package com.guavapay.courierservice.service;

import com.guavapay.courierservice.repository.UserLocationRepository;
import com.guavapay.courierservice.repository.UserRepository;
import com.guavapay.courierservice.entity.Location;
import com.guavapay.courierservice.entity.User;
import com.guavapay.courierservice.model.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;

    public Location getLastLocation(Integer userId) {
        return userLocationRepository.findByLastLocationByUserId(userId).orElseThrow().getLocation();
    }

    public Page<UserResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(this::toUserResponseDto);
    }

    private UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setAvailable(user.isAvailable());
        return userResponseDto;
    }

}
