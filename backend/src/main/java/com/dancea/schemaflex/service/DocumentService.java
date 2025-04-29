package com.dancea.schemaflex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.data.SaveDocumentRequest;
import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.repository.DocumentRepository;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final SchemaService schemaService;

    /*
     * This method retrieves all documents from the database.
     *
     * @return List of Document objects.
     */
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

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

    /*
     * This method retrieves a specific document by its ID.
     * If the document is not found, a 404 error is thrown.
     *
     * @param documentId The ID of the document to retrieve.
     *
     * @return Document object.
     */
    public Document getDocumentById(Integer documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
    }

}
