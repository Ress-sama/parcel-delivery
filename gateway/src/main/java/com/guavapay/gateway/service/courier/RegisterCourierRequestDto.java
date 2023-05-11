package com.guavapay.gateway.service.courier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCourierRequestDto {

    private String fullName;
    private String username;
    private String password;

}
