package com.dancea.schemaflex.errors;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException e) {
        logger.warning("Resource not found: " + e.getMessage());

        Map<String, String> body = new HashMap<String, String>();
        body.put("RESOURCE_NOT_FOUND", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .header("Content-Type", "application/json")
                .body(body);
    }

    @ExceptionHandler(SchemaProcessingException.class)
    public ResponseEntity<Map<String, String>> handleSchemaProcessing(SchemaProcessingException e) {
        logger.warning("Schema processing error: " + e.getMessage());

        Map<String, String> body = new HashMap<String, String>();
        body.put("SCHEMA_PROCESSING_ERROR", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "application/json")
                .body(body);
    }
}
