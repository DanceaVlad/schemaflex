package com.dancea.schemaflex.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dancea.schemaflex.data.ApiResponse;
import com.dancea.schemaflex.data.ChangeDocumentRequest;
import com.dancea.schemaflex.data.CreateDocumentRequest;
import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.errors.GlobalExceptionHandler;
import com.dancea.schemaflex.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/all-documents")
    public ResponseEntity<ApiResponse<List<Document>>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return GlobalExceptionHandler.buildSuccessResponse(documents);
    }

    @PostMapping("/create-document")
    public ResponseEntity<ApiResponse<Void>> createDocument(@RequestBody CreateDocumentRequest createDocumentRequest) {
        documentService.createDocument(createDocumentRequest);
        return GlobalExceptionHandler.buildSuccessResponse(null, HttpStatus.CREATED);
    }

    @PatchMapping("/update-document")
    public ResponseEntity<ApiResponse<Void>> updateDocument(@RequestBody ChangeDocumentRequest changeDocumentRequest) {
        documentService.updateDocument(changeDocumentRequest);
        return GlobalExceptionHandler.buildSuccessResponse();
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<ApiResponse<Document>> getDocumentById(@PathVariable("documentId") Integer documentId) {
        Document document = documentService.getDocumentById(documentId);
        return GlobalExceptionHandler.buildSuccessResponse(document);
    }

}
