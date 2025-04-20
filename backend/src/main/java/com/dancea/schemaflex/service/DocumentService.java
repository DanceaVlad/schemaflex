package com.dancea.schemaflex.service;

import org.springframework.stereotype.Service;

import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.data.SaveDocumentRequest;
import com.dancea.schemaflex.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public void saveDocument(SaveDocumentRequest saveDocumentRequest) {
        documentRepository.save(
                Document.builder()
                        .schemaId(saveDocumentRequest.getSchemaId())
                        .data(saveDocumentRequest.getData().toString())
                        .build());
    }

}
