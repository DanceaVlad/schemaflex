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
    public ResponseEntity<com.dancea.schemaflex.api.model.Document> createDocument(
            CreateDocumentRequest createDocumentRequest) {
        com.dancea.schemaflex.api.model.Document document = documentService.createDocument(createDocumentRequest);
        return ResponseEntity.created(
                null)
                .body(document);

    }

    @Override
    public ResponseEntity<List<com.dancea.schemaflex.api.model.Document>> getAllDocuments() {
        List<com.dancea.schemaflex.data.Document> docs = documentService.getAllDocuments();
        List<Document> apiDocs = docs.stream().map(d -> {
            Document apiDoc = Document.builder()
                    .id(d.getId())
                    .name(d.getName())
                    .schemaId(d.getSchemaId())
                    .build();
            try {
                Map<String, Object> dataMap = objectMapper.readValue(
                        d.getData(),
                        new TypeReference<Map<String, Object>>() {
                        });
                apiDoc.setData(dataMap);
            } catch (Exception e) {
                apiDoc.setData(null);
            }
            return apiDoc;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(apiDocs);

    }

    @Override
    public ResponseEntity<com.dancea.schemaflex.api.model.Document> getDocumentById(Integer documentId) {
        com.dancea.schemaflex.data.Document d = documentService.getDocumentById(documentId);
        Document apiDoc = Document.builder()
                .id(d.getId())
                .name(d.getName())
                .schemaId(d.getSchemaId())
                .build();
        try {
            Map<String, Object> dataMap = objectMapper.readValue(
                    d.getData(),
                    new TypeReference<Map<String, Object>>() {
                    });
            apiDoc.setData(dataMap);
        } catch (Exception e) {
            apiDoc.setData(null);
        }
        return ResponseEntity.ok(apiDoc);
    }

    @Override
    public ResponseEntity<com.dancea.schemaflex.api.model.Document> updateDocument(
            UpdateDocumentRequest updateDocumentRequest) {
        com.dancea.schemaflex.api.model.Document updatedDocument = documentService
                .updateDocument(updateDocumentRequest);
        return ResponseEntity.created(
                null)
                .body(updatedDocument);

    }
}
