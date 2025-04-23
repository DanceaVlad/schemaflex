package com.dancea.schemaflex.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private static final String SCHEMA_PATH = "src/main/resources/schemas/";

    /*
     * This method retrieves all document schemas from the specified directory.
     * It reads both data and UI schema files, ensuring they match by ID.
     * If any discrepancies are found, appropriate exceptions are thrown.
     *
     * @return List of DocumentSchema objects containing data and UI schemas.
     */
    public List<DocumentSchema> getAllDocumentSchemas() {

        ObjectMapper mapper = new ObjectMapper();
        File folder = new File(SCHEMA_PATH);
        File[] listOfDataFiles = folder.listFiles((dir, name) -> name.endsWith(".json") && !name.contains("-ui-"));
        File[] listOfUiFiles = folder.listFiles((dir, name) -> name.endsWith(".json") && name.contains("-ui-"));

        if (listOfDataFiles == null || listOfUiFiles == null) {
            throw new JsonFileNotFoundException("No JSON files found in the schema directory.");
        }
        if (listOfDataFiles.length != listOfUiFiles.length) {
            throw new JsonFileNotFoundException("Mismatch between data and UI schema files.");
        }

        List<DocumentSchema> documentSchemas = new ArrayList<>();

        for (File dataFile : listOfDataFiles) {
            String filename = dataFile.getName();
            String schemaName = filename.substring(0, filename.lastIndexOf('-'));
            int schemaId = getSchemaId(filename);
            File uiFile = findMatchingUiFile(listOfUiFiles, schemaId);

            if (uiFile == null) {
                throw new JsonFileNotFoundException("No matching UI file found for schema ID: " + schemaId);
            }

            try {
                JsonNode dataSchema = mapper.readTree(dataFile);
                JsonNode uiSchema = mapper.readTree(uiFile);

                DocumentSchema documentSchema = DocumentSchema.builder()
                        .id(schemaId)
                        .name(schemaName)
                        .dataSchema(dataSchema)
                        .uiSchema(uiSchema)
                        .build();
                documentSchemas.add(documentSchema);
            } catch (Exception e) {
                throw new InvalidJsonException("Error reading JSON files: " + e.getMessage());
            }
        }

        return documentSchemas;
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
        ObjectMapper mapper = new ObjectMapper();
        File folder = new File(SCHEMA_PATH);
        File[] listOfDataFiles = folder.listFiles((dir, name) -> name.endsWith(".json") && !name.contains("-ui-"));
        File[] listOfUiFiles = folder.listFiles((dir, name) -> name.endsWith(".json") && name.contains("-ui-"));

        if (listOfDataFiles == null || listOfUiFiles == null) {
            throw new JsonFileNotFoundException("No JSON files found in the schema directory.");
        }

        for (File dataFile : listOfDataFiles) {
            String filename = dataFile.getName();
            int schemaId = getSchemaId(filename);

            if (schemaId == id) {
                File uiFile = findMatchingUiFile(listOfUiFiles, schemaId);
                if (uiFile == null) {
                    throw new JsonFileNotFoundException("No matching UI file found for schema ID: " + schemaId);
                }

                try {
                    JsonNode dataSchema = mapper.readTree(dataFile);
                    JsonNode uiSchema = mapper.readTree(uiFile);

                    return DocumentSchema.builder()
                            .id(schemaId)
                            .name(filename.substring(0, filename.lastIndexOf('-')))
                            .dataSchema(dataSchema)
                            .uiSchema(uiSchema)
                            .build();
                } catch (Exception e) {
                    throw new InvalidJsonException("Error reading JSON files: " + e.getMessage());
                }
            }
        }
        throw new JsonFileNotFoundException("No JSON file found with ID: " + id);
    }

    /*
     * This method fetches the data schema based on the provided schema ID.
     * It searches through the data schema files in the specified directory.
     * If a matching file is found, it reads and returns the JSON content.
     * If no matching file is found, an exception is thrown.
     *
     * @param id The schema ID to search for.
     *
     * @return The JSON content of the matching data schema file.
     */
    public JsonNode getDataSchemaById(Integer id) {
        ObjectMapper mapper = new ObjectMapper();
        File folder = new File(SCHEMA_PATH);
        File[] listOfDataFiles = folder.listFiles((dir, name) -> name.endsWith(".json") && !name.contains("-ui-"));

        if (listOfDataFiles == null) {
            throw new JsonFileNotFoundException("No JSON files found in the schema directory.");
        }

        for (File dataFile : listOfDataFiles) {
            String filename = dataFile.getName();
            int schemaId = getSchemaId(filename);

            if (schemaId == id) {
                try {
                    return mapper.readTree(dataFile);
                } catch (Exception e) {
                    throw new InvalidJsonException("Error reading JSON file: " + e.getMessage());
                }
            }
        }
        throw new JsonFileNotFoundException("No JSON file found with ID: " + id);
    }

    /*
     * This method extracts the schema ID from the filename.
     * It assumes the ID is located between the last '-' and the last '.' in the
     * filename.
     *
     * e.g., "schema-123.json" would yield 123.
     *
     * @param filename The name of the file from which to extract the schema ID.
     *
     * @return The extracted schema ID as an integer.
     */
    private int getSchemaId(String filename) {
        String idString = filename.substring(filename.lastIndexOf('-') + 1, filename.lastIndexOf('.'));
        try {
            return Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            throw new InvalidJsonException("Invalid schema ID in filename: " + filename);
        }
    }

    /*
     * This method finds the UI file that matches the given schema ID.
     * It assumes the UI file name contains the schema ID in the format
     * "-ui-<schemaId>".
     *
     * @param uiFiles Array of UI files to search through.
     *
     * @param schemaId The schema ID to match against.
     *
     * @return The matching UI file, or null if no match is found.
     */
    private File findMatchingUiFile(File[] uiFiles, int schemaId) {
        for (File uiFile : uiFiles) {
            String filename = uiFile.getName();
            if (filename.contains("-ui-" + schemaId)) {
                return uiFile;
            }
        }
        return null;
    }
}
