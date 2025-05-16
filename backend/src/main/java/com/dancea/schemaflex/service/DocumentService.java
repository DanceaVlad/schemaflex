package com.dancea.schemaflex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.ChangeDocumentRequest;
import com.dancea.schemaflex.data.CreateDocumentRequest;
import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document getDocumentById(Integer documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
    }

    public void createDocument(CreateDocumentRequest createDocumentRequest) {

        // TODO: Validate the document data against the schema

        documentRepository.save(
                Document.builder()
                        .name(createDocumentRequest.getName() == null || createDocumentRequest.getName().isEmpty()
                                ? "Untitled"
                                : createDocumentRequest.getName())
                        .schemaId(createDocumentRequest.getSchemaId())
                        .data(createDocumentRequest.getData().toString())
                        .build());
    }

    public void updateDocument(ChangeDocumentRequest changeDocumentRequest) {
        Document document = documentRepository.findById(changeDocumentRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with ID: " + changeDocumentRequest.getId()));

        // TODO: Validate the document data against the schema

        document.setData(changeDocumentRequest.getData().toString());
        documentRepository.save(document);
    }

}
