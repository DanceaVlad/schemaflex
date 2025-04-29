package com.dancea.schemaflex.errors;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dancea.schemaflex.data.ApiResponse;
import com.dancea.schemaflex.data.ErrorBody;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJsonException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidJson(InvalidJsonException e) {
        return buildErrorResponse(Map.of("INVALID_JSON", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonFileNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleJsonFileNotFound(JsonFileNotFoundException e) {
        return buildErrorResponse(Map.of("SCHEMA_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException e) {
        return buildErrorResponse(Map.of("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<ApiResponse<T>> buildErrorResponse(Map<String, String> errors, HttpStatus status) {
        ErrorBody[] errorBodies = errors.entrySet().stream()
                .map(entry -> new ErrorBody(entry.getKey(), entry.getValue()))
                .toArray(ErrorBody[]::new);
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(false)
                .errors(errorBodies)
                .data(null)
                .build();
        return ResponseEntity.status(status)
                .header("Content-Type", "application/json")
                .body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse() {
        return buildSuccessResponse(null);
    }

    public static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse(T data) {
        return buildSuccessResponse(data, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ApiResponse<T>> buildSuccessResponse(T data, HttpStatus status) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(response);
    }
}
