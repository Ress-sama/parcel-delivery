package com.guavapay.courierservice.configuration;

import com.guavapay.courierservice.common.SecurityUtil;
import com.guavapay.courierservice.common.TokenProvider;
import com.guavapay.courierservice.error.BaseErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.guavapay.courierservice.error.ErrorHandlerUtil.buildHttpErrorResponse;


@RequiredArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            String token = SecurityUtil.extractToken(httpServletRequest);
            org.springframework.security.core.Authentication authentication = tokenProvider
                    .parseAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof JwtException) {
                if (e instanceof ExpiredJwtException) {
                    buildHttpErrorResponse(response, new BaseErrorResponse("JWT expired",
                            "MS_COURIER_001",
                            "JWT expired", 406), 406);
                    return;
                }
                buildHttpErrorResponse(response, new BaseErrorResponse("JWT wrong",
                        "MS_COURIER_002",
                        "JWT wrong", 401), 401);
                return;
            }
            chain.doFilter(request, response);
        }
    }

}
