package com.dancea.schemaflex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.api.model.ChangeDocumentRequest;
import com.dancea.schemaflex.api.model.CreateDocumentRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.repository.DocumentRepository;

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

    public void createDocument(CreateDocumentRequest createDocumentRequest) {
        // TODO: Validate the document data against the schema
        JsonNode dataNode = objectMapper.convertValue(createDocumentRequest.getData(), JsonNode.class);
        documentRepository.save(
                Document.builder()
                        .name(createDocumentRequest.getName() == null || createDocumentRequest.getName().isEmpty()
                                ? "Untitled"
                                : createDocumentRequest.getName())
                        .schemaId(createDocumentRequest.getSchemaId())
                        .data(dataNode.toString())
                        .build());
    }

    public void updateDocument(ChangeDocumentRequest changeDocumentRequest) {
        Document document = documentRepository.findById(changeDocumentRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with ID: " + changeDocumentRequest.getId()));
        // TODO: Validate the document data against the schema
        JsonNode dataNode = objectMapper.convertValue(changeDocumentRequest.getData(), JsonNode.class);
        document.setData(dataNode.toString());
        documentRepository.save(document);
    }

}
