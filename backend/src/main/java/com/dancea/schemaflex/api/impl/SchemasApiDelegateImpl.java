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
        List<com.dancea.schemaflex.data.CustomSchema> schemas = schemaService.getAllSchemas();
        List<CustomSchema> apiSchemas = schemas.stream().map(s -> {
            CustomSchema apiSchema = CustomSchema.builder()
                    .id(s.getId())
                    .name(s.getName())
                    .dataSchema(objectMapper.convertValue(s.getDataSchema(), new TypeReference<Map<String, Object>>() {
                    }))
                    .uiSchema(objectMapper.convertValue(s.getUiSchema(), new TypeReference<Map<String, Object>>() {
                    }))
                    .build();
            return apiSchema;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(apiSchemas);

    }

    @Override
    public ResponseEntity<CustomSchema> getSchemaById(Integer schemaId) {
        com.dancea.schemaflex.data.CustomSchema s = schemaService.getSchemaById(schemaId);
        CustomSchema apiSchema = CustomSchema.builder()
                .id(s.getId())
                .name(s.getName())
                .dataSchema(objectMapper.convertValue(s.getDataSchema(), new TypeReference<Map<String, Object>>() {
                }))
                .uiSchema(objectMapper.convertValue(s.getUiSchema(), new TypeReference<Map<String, Object>>() {
                }))
                .build();

        return ResponseEntity.ok(apiSchema);

    }

    @Override
    public ResponseEntity<Void> createSchema(CreateSchemaRequest createSchemaRequest) {
        schemaService.createSchema(createSchemaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
