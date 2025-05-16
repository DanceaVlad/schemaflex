package com.dancea.schemaflex.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.CreateSchemaRequest;
import com.dancea.schemaflex.data.Schema;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.repository.SchemaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchemaService {

    private final SchemaRepository schemaRepository;

    public List<Schema> getAllSchemas() {
        return schemaRepository.findAll();
    }

    public Schema getSchemaById(Integer id) {
        return schemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schema with ID " + id + " not found"));
    }

    public void createSchema(CreateSchemaRequest request) {
        Schema saved = schemaRepository.save(Schema.builder()
                .name(request.getSchemaName())
                .dataSchema(request.getDataSchema())
                .uiSchema(request.getUiSchema())
                .build());

        if (request.isSaveAsFile()) {
            // TODO: Implement file saving logic
        }
    }
}
