package com.guavapay.courierservice.common;

import com.guavapay.courierservice.model.ChangePasswordRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
@RequiredArgsConstructor
public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher, ChangePasswordRequestDto> {

//    final LocaleMessageResolver messageResolver;

    @Override
    public boolean isValid(ChangePasswordRequestDto value, final ConstraintValidatorContext context) {
        String newPassword = value.getNewPassword();
        String repeatNewPassword = value.getRepeatNewPassword();

        String errorCode = "ErrorCode.INCORRECT_REPEAT_PASSWORD";
        if (!newPassword.equals(repeatNewPassword)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("messageResolver.resolve(errorCode)")
                    .addPropertyNode("repeatNewPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}