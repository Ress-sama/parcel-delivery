package com.guavapay.userservice.common;

import com.guavapay.userservice.model.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public static String extractToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            token = httpServletRequest.getParameter("token");
            if (token == null) {
                throw new RuntimeException(token);
            } else
                return token;
        }
        if (!token.startsWith("Bearer")) {
            throw new RuntimeException(token);
        }
        return token.substring(7);
    }

    public UserDetails getPrincipal() {
        var context = SecurityContextHolder.getContext();
        if (Objects.nonNull(context)) {
            var authentication = context.getAuthentication();
            if (Objects.nonNull(authentication)) {
                return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }
        throw new RuntimeException();
    }

}
