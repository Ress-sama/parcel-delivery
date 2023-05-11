package com.guavapay.gateway.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guavapay.gateway.common.BaseErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<BaseErrorResponse> handleEntityNotFoundException(FeignException ex) {
        ex.printStackTrace();
        try {
            BaseErrorResponse baseErrorResponse = objectMapper.readValue(ex.getMessage(), BaseErrorResponse.class);
            return ResponseEntity.status(baseErrorResponse.getStatus())
                    .body(new BaseErrorResponse(baseErrorResponse.getMessage(),
                            baseErrorResponse.getCode(), baseErrorResponse.getReason(), baseErrorResponse.getStatus()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
