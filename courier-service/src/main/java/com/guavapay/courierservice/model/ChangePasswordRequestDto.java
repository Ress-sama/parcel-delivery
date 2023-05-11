package com.guavapay.courierservice.model;

import com.guavapay.courierservice.common.PasswordMatcher;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordMatcher
public class ChangePasswordRequestDto {

    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String repeatNewPassword;

    @Override
    public String toString() {
        return "ChangePasswordRequestDto{" +
                "oldPassword='" + "[PROTECTED]" + '\'' +
                ", newPassword='" + "[PROTECTED]" + '\'' +
                ", repeatNewPassword='" + "[PROTECTED]" + '\'' +
                '}';
    }

}
