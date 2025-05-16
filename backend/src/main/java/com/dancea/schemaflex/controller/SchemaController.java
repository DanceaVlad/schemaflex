package com.dancea.schemaflex.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dancea.schemaflex.data.ApiResponse;
import com.dancea.schemaflex.data.CreateSchemaRequest;
import com.dancea.schemaflex.data.Schema;
import com.dancea.schemaflex.errors.GlobalExceptionHandler;
import com.dancea.schemaflex.service.SchemaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schemas")
@RequiredArgsConstructor
public class SchemaController {

    private final SchemaService schemaService;

    @GetMapping("/all-schemas")
    public ResponseEntity<ApiResponse<List<Schema>>> getAllSchemas() {
        List<Schema> schemas = schemaService.getAllSchemas();
        return GlobalExceptionHandler.buildSuccessResponse(schemas);
    }

    @GetMapping("/schema/{schemaId}")
    public ResponseEntity<ApiResponse<Schema>> getSchemaById(
            @PathVariable("schemaId") Integer schemaId) {
        Schema schema = schemaService.getSchemaById(schemaId);
        return GlobalExceptionHandler.buildSuccessResponse(schema);
    }

    @PostMapping("/create-schema")
    public ResponseEntity<ApiResponse<Void>> createSchema(
            @RequestBody CreateSchemaRequest request) {
        schemaService.createSchema(request);
        return GlobalExceptionHandler.buildSuccessResponse(null, HttpStatus.CREATED);
    }
}
