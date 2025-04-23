package com.dancea.schemaflex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dancea.schemaflex.data.ApiResponse;
import com.dancea.schemaflex.data.Document;
import com.dancea.schemaflex.data.SaveDocumentRequest;
import com.dancea.schemaflex.errors.GlobalExceptionHandler;
import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.dancea.schemaflex.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    /*
     * This method retrieves all documents from the database.
     *
     * @return ResponseEntity containing a list of Document objects.
     */
    @GetMapping("/all-documents")
    public ResponseEntity<ApiResponse<List<Document>>> getAllDocuments() {
        return GlobalExceptionHandler.buildSuccessResponse(documentService.getAllDocuments());
    }

    /*
     * This method saves a document based on the provided SaveDocumentRequest.
     *
     * @param saveDocumentRequest The request object containing the document data to
     * be saved.
     *
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PostMapping("/save-document")
    public ResponseEntity<ApiResponse<Void>> saveDocument(@RequestBody SaveDocumentRequest saveDocumentRequest) {
        try {
            documentService.saveDocument(saveDocumentRequest);
            return GlobalExceptionHandler.buildSuccessResponse();
        } catch (JsonFileNotFoundException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("SCHEMA_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidJsonException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("INVALID_JSON", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
