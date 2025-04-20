package com.dancea.schemaflex.service;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.data.SaveDocumentRequest;
import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.dancea.schemaflex.repository.DocumentRepository;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final SchemaService schemaService;

    /*
     * This method saves a document based on the provided SaveDocumentRequest.
     * It fetches the schema based on the schema ID and validates the document data
     * If the schema is not found or the data is invalid, an exception is thrown.
     *
     * @param saveDocumentRequest The request object containing the document data to
     * be saved.
     */
    public void saveDocument(SaveDocumentRequest saveDocumentRequest) {

        try {
            JsonNode dataSchema = schemaService.getDataSchemaById(saveDocumentRequest.getSchemaId());
        } catch (JsonFileNotFoundException e) {
            throw new JsonFileNotFoundException("Schema not found for ID: " + saveDocumentRequest.getSchemaId());
        } catch (InvalidJsonException e) {
            throw new InvalidJsonException("Invalid JSON data: " + e.getMessage());
        }

        // TODO: Validate the document data against the schema
        if (false) {
            throw new InvalidJsonException("Document data does not match the schema.");
        }

        documentRepository.save(
                Document.builder()
                        .schemaId(saveDocumentRequest.getSchemaId())
                        .data(saveDocumentRequest.getData().toString())
                        .build());
    }

}
