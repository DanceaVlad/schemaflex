package com.dancea.schemaflex.api.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dancea.schemaflex.api.SchemasApiDelegate;
import com.dancea.schemaflex.api.model.CreateSchemaRequest;
import com.dancea.schemaflex.api.model.CustomSchema;
import com.dancea.schemaflex.service.SchemaService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchemasApiDelegateImpl implements SchemasApiDelegate {
    private final SchemaService schemaService;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<List<CustomSchema>> getAllSchemas() {
        List<CustomSchema> apiSchemas = schemaService.getAllSchemas()
                .stream()
                .map(this::toApiSchema)
                .collect(Collectors.toList());
        return ResponseEntity.ok(apiSchemas);
    }

    @Override
    public ResponseEntity<CustomSchema> getSchemaById(Integer schemaId) {
        com.dancea.schemaflex.data.CustomSchema s = schemaService.getSchemaById(schemaId);
        CustomSchema apiSchema = toApiSchema(s);
        return ResponseEntity.ok(apiSchema);
    }

    @Override
    public ResponseEntity<Void> createSchema(CreateSchemaRequest createSchemaRequest) {
        schemaService.createSchema(createSchemaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private CustomSchema toApiSchema(com.dancea.schemaflex.data.CustomSchema s) {
        return CustomSchema.builder()
                .id(s.getId())
                .name(s.getName())
                .dataSchema(convertToMap(s.getDataSchema()))
                .uiSchema(convertToMap(s.getUiSchema()))
                .build();
    }

    private Map<String, Object> convertToMap(Object schema) {
        return objectMapper.convertValue(schema, new TypeReference<Map<String, Object>>() {
        });
    }
}
