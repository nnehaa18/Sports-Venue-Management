package com.assignment.handler;

import com.assignment.exception.SlotAlreadyBookedException;
import com.assignment.exception.SlotNotAvailableException;
import com.assignment.wrapper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SlotAlreadyBookedException.class)
    public ResponseEntity<ApiResponse<Void>> handleSlotBooked (RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(SlotNotAvailableException.class)
    public ResponseEntity<ApiResponse<Void>> handleSlotUnavailable (RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Something went wrong: "+ex.getMessage()));
    }

}
