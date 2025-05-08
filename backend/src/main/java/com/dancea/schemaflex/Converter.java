package com.dancea.schemaflex;

import com.dancea.schemaflex.errors.SchemaProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

@jakarta.persistence.Converter(autoApply = true)
public class Converter implements AttributeConverter<JsonNode, String> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        if (attribute == null)
            return null;
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new SchemaProcessingException("Could not convert JSON to string for database");
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        try {
            return mapper.readTree(dbData);
        } catch (Exception e) {
            throw new SchemaProcessingException("Could not parse JSON from database");
        }
    }
}
