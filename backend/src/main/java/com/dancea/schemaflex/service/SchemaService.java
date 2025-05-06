package com.dancea.schemaflex.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.DocumentSchema;
import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchemaService {

    // TODO this should be configurable. Ideally, adding a schema should not require
    // redeploying the application.
    // TODO please make sure to include the RDA DMP common schema here for testing:
    // https://github.com/RDA-DMP-Common/RDA-DMP-Common-Standard/blob/master/examples/JSON/JSON-schema/1.0/maDMP-schema-1.0.json

    private static final String CLASSPATH_SCHEMAS = "classpath:schemas/*.json";

    private final ObjectMapper mapper;
    private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    /*
     * This method retrieves all document schemas from the specified directory.
     * It reads both data and UI schema files, ensuring they match by ID.
     * If any discrepancies are found, appropriate exceptions are thrown.
     *
     * @return List of DocumentSchema objects containing data and UI schemas.
     */
    public List<DocumentSchema> getAllDocumentSchemas() {
        Resource[] all = loadAllSchemaResources();
        Map<Integer, Resource> dataFiles = new HashMap<>();
        Map<Integer, Resource> uiFiles = new HashMap<>();

        for (Resource res : all) {
            String name = Objects.requireNonNull(res.getFilename());
            int id = extractId(name);
            if (name.contains("-ui-"))
                uiFiles.put(id, res);
            else
                dataFiles.put(id, res);
        }

        if (dataFiles.isEmpty() || uiFiles.isEmpty()) {
            throw new JsonFileNotFoundException("No JSON files found in schemas folder.");
        }
        if (!dataFiles.keySet().equals(uiFiles.keySet())) {
            throw new JsonFileNotFoundException("Mismatch between data and UI schema IDs.");
        }

        return dataFiles.keySet().stream()
                .sorted()
                .map(id -> loadDocumentSchema(id, dataFiles.get(id), uiFiles.get(id)))
                .collect(Collectors.toList());
    }

    /*
     * This method retrieves a specific document schema by its ID.
     * If the schema ID is not found, an exception is thrown.
     *
     * @param schemaId The ID of the schema to retrieve.
     *
     * @return The DocumentSchema object containing the data and UI schemas.
     */
    public DocumentSchema getDocumentSchemaById(Integer id) {
        List<DocumentSchema> all = getAllDocumentSchemas();
        return all.stream()
                .filter(ds -> ds.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new JsonFileNotFoundException("No JSON schema found with ID: " + id));
    }

    /**
     * Return just the data schema JSON by ID.
     */
    public JsonNode getDataSchemaById(Integer id) {
        Resource data = loadAllSchemaResourcesAsList().stream()
                .filter(r -> r.getFilename() != null
                        && !r.getFilename().contains("-ui-")
                        && extractId(r.getFilename()) == id)
                .findFirst()
                .orElseThrow(() -> new JsonFileNotFoundException("No data schema found with ID: " + id));
        return readJson(data);
    }

    // ─── Helpers

    private Resource[] loadAllSchemaResources() {
        try {
            return resolver.getResources(CLASSPATH_SCHEMAS);
        } catch (IOException e) {
            throw new JsonFileNotFoundException("Could not load schema resources: " + e.getMessage());
        }
    }

    private List<Resource> loadAllSchemaResourcesAsList() {
        return Arrays.asList(loadAllSchemaResources());
    }

    private DocumentSchema loadDocumentSchema(int id, Resource dataRes, Resource uiRes) {
        JsonNode data = readJson(dataRes);
        JsonNode ui = readJson(uiRes);
        String baseName = Objects.requireNonNull(dataRes.getFilename())
                .replaceFirst("-\\d+\\.json$", "");
        return DocumentSchema.builder()
                .id(id)
                .name(baseName)
                .dataSchema(data)
                .uiSchema(ui)
                .build();
    }

    private JsonNode readJson(Resource res) {
        try (InputStream in = res.getInputStream()) {
            return mapper.readTree(in);
        } catch (IOException e) {
            throw new InvalidJsonException("Error reading JSON file "
                    + res.getFilename() + ": " + e.getMessage());
        }
    }

    /**
     * Extract integer ID from filenames like "foo-123.json" or "foo-ui-123.json"
     */
    private int extractId(String filename) {
        try {
            String num = filename.substring(filename.lastIndexOf('-') + 1,
                    filename.lastIndexOf('.'));
            return Integer.parseInt(num);
        } catch (Exception e) {
            throw new InvalidJsonException("Invalid schema ID in filename: " + filename);
        }
    }
}
