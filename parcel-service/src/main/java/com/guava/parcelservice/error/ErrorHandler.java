package com.guava.parcelservice.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(400).body(new BaseErrorResponse("Data not found",
                "PARCEL_SERVICE_001", ex.getMessage(), 400));
    }

    @ExceptionHandler(ProcessIsNotPossibleException.class)
    public ResponseEntity<BaseErrorResponse> handleEntityNotFoundException(ProcessIsNotPossibleException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(400).body(new BaseErrorResponse("Process is not possible",
                "PARCEL_SERVICE_002", ex.getMessage(), 400));
    }

}