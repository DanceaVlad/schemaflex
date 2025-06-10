package com.dancea.schemaflex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.api.model.CreateDocumentRequest;
import com.dancea.schemaflex.api.model.UpdateDocumentRequest;
import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.repository.DocumentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ObjectMapper objectMapper;

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Integer documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
    }

    public com.dancea.schemaflex.api.model.Document createDocument(CreateDocumentRequest createDocumentRequest) {
        // TODO: Validate the document data against the schema
        JsonNode dataNode = objectMapper.convertValue(createDocumentRequest.getData(), JsonNode.class);
        Document savedDocument = documentRepository.save(
                Document.builder()
                        .name(createDocumentRequest.getName() == null || createDocumentRequest.getName().isEmpty()
                                ? "Untitled"
                                : createDocumentRequest.getName())
                        .schemaId(createDocumentRequest.getSchemaId())
                        .data(dataNode.toString())
                        .build());

        return com.dancea.schemaflex.api.model.Document.builder()
                .id(savedDocument.getId())
                .name(savedDocument.getName())
                .schemaId(savedDocument.getSchemaId())
                .data(objectMapper.convertValue(dataNode,
                        new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {
                        }))
                .build();

    }

    public com.dancea.schemaflex.api.model.Document updateDocument(UpdateDocumentRequest updateDocumentRequest) {
        // TODO: Validate the document data against the schema
        Document document = documentRepository.findById(updateDocumentRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with ID: " + updateDocumentRequest.getId()));
        JsonNode dataNode = objectMapper.convertValue(updateDocumentRequest.getData(), JsonNode.class);
        document.setData(dataNode.toString());
        Document updatedDocument = documentRepository.save(document);

        return com.dancea.schemaflex.api.model.Document.builder()
                .id(updatedDocument.getId())
                .name(updatedDocument.getName())
                .schemaId(updatedDocument.getSchemaId())
                .data(objectMapper.convertValue(dataNode,
                        new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {
                        }))
                .build();
    }

}
