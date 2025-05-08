package com.dancea.schemaflex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.dancea.schemaflex.data.Schema;
import com.dancea.schemaflex.errors.SchemaProcessingException;
import com.dancea.schemaflex.repository.SchemaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SchemaRepository schemaRepository;

    private static final String CLASSPATH_SCHEMAS = "classpath:schemas/*.json";
    private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());

    private Resource[] loadAllSchemaResources() {
        try {
            return resolver.getResources(CLASSPATH_SCHEMAS);
        } catch (IOException e) {
            throw new SchemaProcessingException("Failed to load schema resources");
        }
    }

    private String extractSchemaName(String filename) {
        if (filename == null || filename.isEmpty() || filename.indexOf(".json") == -1) {
            throw new SchemaProcessingException("Invalid filename: " + filename);
        }

        if (filename.contains("-ui")) {
            return filename.substring(0, filename.indexOf("-"));
        } else {
            return filename.substring(0, filename.indexOf("."));
        }
    }

    @Override
    public void run(String... args) throws Exception {

        Resource[] all = loadAllSchemaResources();
        Map<String, Resource> dataFiles = new HashMap<>();
        Map<String, Resource> uiFiles = new HashMap<>();

        for (Resource res : all) {
            String name = res.getFilename();
            if (name == null) {
                continue;
            }
            String schemaName = extractSchemaName(name);
            if (name.contains("-ui")) {
                uiFiles.put(schemaName, res);
            } else {
                dataFiles.put(schemaName, res);
            }
        }

        if (dataFiles.isEmpty() || uiFiles.isEmpty()) {
            throw new SchemaProcessingException("No JSON files found in schemas folder.");
        }
        if (!dataFiles.keySet().equals(uiFiles.keySet())) {
            throw new SchemaProcessingException("Mismatch between data and UI schema names.");
        }

        for (String schemaName : dataFiles.keySet()) {
            try {
                Resource dataResource = dataFiles.get(schemaName);
                Resource uiResource = uiFiles.get(schemaName);

                JsonNode dataNode = objectMapper.readTree(dataResource.getInputStream());
                JsonNode uiNode = objectMapper.readTree(uiResource.getInputStream());

                Schema schema = Schema.builder()
                        .name(schemaName)
                        .dataSchema(dataNode)
                        .uiSchema(uiNode)
                        .build();

                schemaRepository.save(schema);
                logger.info("Schema " + schemaName + " loaded successfully.");
            } catch (IOException e) {
                throw new SchemaProcessingException("Failed to parse schema JSON for " + schemaName);
            }
        }

    }

}
