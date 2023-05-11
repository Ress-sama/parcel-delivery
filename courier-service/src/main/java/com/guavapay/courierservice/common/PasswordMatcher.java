package com.guavapay.courierservice.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordMatcherValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatcher {

    String message() default "ErrorCode.INCORRECT_REPEAT_PASSWORD";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}