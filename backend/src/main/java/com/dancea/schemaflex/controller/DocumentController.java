package com.dancea.schemaflex.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dancea.schemaflex.data.ApiResponse;
import com.dancea.schemaflex.data.SaveDocumentRequest;
import com.dancea.schemaflex.errors.GlobalExceptionHandler;
import com.dancea.schemaflex.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping("/save-document")
    public ResponseEntity<ApiResponse<Void>> saveDocument(@RequestBody SaveDocumentRequest saveDocumentRequest) {
        try {
            documentService.saveDocument(saveDocumentRequest);
            return GlobalExceptionHandler.buildSuccessResponse();
        } catch (Exception e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
