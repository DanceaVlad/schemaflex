package com.dancea.schemaflex.errors;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dancea.schemaflex.data.ApiResponse;
import com.dancea.schemaflex.data.ErrorBody;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFound(ResourceNotFoundException e) {
        logger.warning("Resource not found: " + e.getMessage());
        return buildErrorResponse(Map.of("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SchemaProcessingException.class)
    public ResponseEntity<ApiResponse<String>> handleSchemaProcessing(SchemaProcessingException e) {
        logger.warning("Schema processing error: " + e.getMessage());
        return buildErrorResponse(Map.of("SCHEMA_PROCESSING_ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
