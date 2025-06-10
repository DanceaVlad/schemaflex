package com.dancea.schemaflex.api.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dancea.schemaflex.api.DocumentsApiDelegate;
import com.dancea.schemaflex.api.model.CreateDocumentRequest;
import com.dancea.schemaflex.api.model.Document;
import com.dancea.schemaflex.api.model.UpdateDocumentRequest;
import com.dancea.schemaflex.service.DocumentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentsApiDelegateImpl implements DocumentsApiDelegate {
    private final DocumentService documentService;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<Document> createDocument(CreateDocumentRequest createDocumentRequest) {
        Document document = documentService.createDocument(createDocumentRequest);
        return ResponseEntity.created(null).body(document);
    }

    @Override
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> apiDocs = documentService.getAllDocuments()
                .stream()
                .map(this::toApiDocument)
                .collect(Collectors.toList());
        return ResponseEntity.ok(apiDocs);
    }

    @Override
    public ResponseEntity<Document> getDocumentById(Integer documentId) {
        com.dancea.schemaflex.data.Document d = documentService.getDocumentById(documentId);
        Document apiDoc = toApiDocument(d);
        return ResponseEntity.ok(apiDoc);
    }

    @Override
    public ResponseEntity<Document> updateDocument(UpdateDocumentRequest updateDocumentRequest) {
        Document updatedDocument = documentService.updateDocument(updateDocumentRequest);
        return ResponseEntity.created(null).body(updatedDocument);
    }

    private Document toApiDocument(com.dancea.schemaflex.data.Document d) {
        return Document.builder()
                .id(d.getId())
                .name(d.getName())
                .schemaId(d.getSchemaId())
                .data(parseData(d.getData()))
                .build();
    }

    private Map<String, Object> parseData(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            return null;
        }
    }
}
