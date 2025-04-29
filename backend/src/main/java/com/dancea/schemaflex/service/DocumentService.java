package com.dancea.schemaflex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.ChangeDocumentRequest;
import com.dancea.schemaflex.data.CreateDocumentRequest;
import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.repository.DocumentRepository;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.ValidationException;
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
    public void createDocument(CreateDocumentRequest createDocumentRequest) {

        try {
            JsonNode dataSchema = schemaService.getDataSchemaById(createDocumentRequest.getSchemaId());
        } catch (JsonFileNotFoundException e) {
            throw new JsonFileNotFoundException("Schema not found for ID: " + createDocumentRequest.getSchemaId());
        } catch (InvalidJsonException e) {
            throw new InvalidJsonException("Invalid JSON data: " + e.getMessage());
        }

        // TODO: Validate the document data against the schema
        if (false) {
            throw new ValidationException("Document data does not match the schema.");
        }

        documentRepository.save(
                Document.builder()
                        .schemaId(createDocumentRequest.getSchemaId())
                        .data(createDocumentRequest.getData().toString())
                        .build());
    }

    /*
     * This method updates a document based on the provided ChangeDocumentRequest.
     * It fetches the schema based on the schema ID and validates the document data
     * If the schema is not found or the data is invalid, an exception is thrown.
     *
     * @param changeDocumentRequest The request object containing the document data
     * to be updated.
     */
    public void updateDocument(ChangeDocumentRequest changeDocumentRequest) {
        Document document = documentRepository.findById(changeDocumentRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with ID: " + changeDocumentRequest.getId()));
        try {
            JsonNode dataSchema = schemaService.getDataSchemaById(document.getSchemaId());
        } catch (JsonFileNotFoundException e) {
            throw new JsonFileNotFoundException("Schema not found for ID: " + document.getSchemaId());
        } catch (InvalidJsonException e) {
            throw new InvalidJsonException("Invalid JSON data: " + e.getMessage());
        }

        // TODO: Validate the document data against the schema
        if (false) {
            throw new ValidationException("Document data does not match the schema.");
        }

        document.setData(changeDocumentRequest.getData().toString());
        documentRepository.save(document);
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
