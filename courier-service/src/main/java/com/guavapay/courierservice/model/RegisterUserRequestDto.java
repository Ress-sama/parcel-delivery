package com.guavapay.courierservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterUserRequestDto {

    @NotBlank
    private String fullName;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @Override
    public String toString() {
        return "UserRequestDto{" +
                ", tempPassword='" + "[PROTECTED]" + '\'' +
                '}';
    }

}
