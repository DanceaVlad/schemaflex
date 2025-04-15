package com.dancea.schemaflex.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchemaService {

    private static final String SCHEMA_PATH = "src/main/resources/schemas/";

    public List<JsonNode> getAllSchemas() {

        List<JsonNode> schemas = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        File folder = new File(SCHEMA_PATH);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (listOfFiles == null || listOfFiles.length == 0) {
            // Handles the case when the directory is empty or does not exist.
            throw new JsonFileNotFoundException("No JSON files found in the directory: " + SCHEMA_PATH);
        }

        for (File file : listOfFiles) {
            try {
                JsonNode schema = mapper.readTree(file);
                schemas.add(schema);
            } catch (JsonProcessingException e) {
                // Handles the case when the JSON is invalid.
                throw new InvalidJsonException("Invalid JSON format in file: " + file.getName());
            } catch (IOException e) {
                // Handles lower-level IO issues.
                throw new JsonFileNotFoundException("Unable to read schema file: " + file.getName());
            }
        }

        return schemas;
    }
}
