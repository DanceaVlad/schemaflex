package com.dancea.schemaflex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.api.model.CreateSchemaRequest;
import com.dancea.schemaflex.data.CustomSchema;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.repository.SchemaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchemaService {

    private final SchemaRepository schemaRepository;
    private final ObjectMapper objectMapper;

    public List<CustomSchema> getAllSchemas() {
        return schemaRepository.findAll();
    }

    public CustomSchema getSchemaById(Integer id) {
        return schemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schema with ID " + id + " not found"));
    }

    public void createSchema(CreateSchemaRequest request) {
        JsonNode dataSchemaNode = objectMapper.convertValue(request.getDataSchema(), JsonNode.class);
        JsonNode uiSchemaNode = objectMapper.convertValue(request.getUiSchema(), JsonNode.class);
        CustomSchema saved = schemaRepository.save(CustomSchema.builder()
                .name(request.getSchemaName())
                .dataSchema(dataSchemaNode)
                .uiSchema(uiSchemaNode)
                .build());

        // TODO: Implement file saving logic
    }
}
