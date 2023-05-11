package com.guavapay.courierservice.model;

import lombok.Data;

@Data
public class UserResponseDto {

    private Integer id;
    private String fullName;
    private String username;
    private boolean available;

}
