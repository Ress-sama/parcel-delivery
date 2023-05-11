package com.guavapay.gateway.service.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAdminRequestDto {

    private String fullName;
    private String username;
    private String password;

}
