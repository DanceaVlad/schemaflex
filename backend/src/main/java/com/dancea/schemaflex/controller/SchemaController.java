package com.dancea.schemaflex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dancea.schemaflex.data.ApiResponse;
import com.dancea.schemaflex.errors.GlobalExceptionHandler;
import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.dancea.schemaflex.service.SchemaService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schemas")
@RequiredArgsConstructor
public class SchemaController {

    private final SchemaService schemaService;

    @GetMapping("/all-schemas")
    public ResponseEntity<ApiResponse<List<JsonNode>>> getAllSchemas() {
        try {
            return GlobalExceptionHandler.buildSuccessResponse(schemaService.getAllSchemas());
        } catch (JsonFileNotFoundException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("SCHEMA_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidJsonException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("INVALID_JSON", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
