package com.guavapay.userservice.model;

import lombok.Data;

@Data
public class UserResponseDto {

    private Integer id;
    private String fullName;
    private String username;

}
