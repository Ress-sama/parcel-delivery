package com.guavapay.gateway.service.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserServiceRequestDto {

    private String fullName;
    private String username;
    private String password;

}
