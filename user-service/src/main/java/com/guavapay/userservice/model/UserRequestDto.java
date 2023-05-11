package com.guavapay.userservice.model;

import lombok.Data;

@Data
public class UserRequestDto {

    private String fullName;
    private String username;
    private String password;

}
