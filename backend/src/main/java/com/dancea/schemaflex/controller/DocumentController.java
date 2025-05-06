package com.dancea.schemaflex.controller;

import java.util.List;
import java.util.Map;

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
import com.dancea.schemaflex.errors.InvalidJsonException;
import com.dancea.schemaflex.errors.JsonFileNotFoundException;
import com.dancea.schemaflex.errors.ResourceNotFoundException;
import com.dancea.schemaflex.errors.ValidationException;
import com.dancea.schemaflex.service.DocumentService;

import lombok.RequiredArgsConstructor;

// TODO it seems that this controller is not generated from an OpenAPI document or the OpenAPI document is missing.
//      this means that the endpoint handling will need to be separately coded in the backend and the frontend. Part
//      of this exercise is necessary to avoid duplicating work on the frontend and backend. Please find a way to
//      generate both the frontend and the backend code from an OpenAPI document.

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
    @PostMapping("/create-document")
    public ResponseEntity<ApiResponse<Void>> createDocument(@RequestBody CreateDocumentRequest createDocumentRequest) {
        // TODO: it seems that this code segment is repeated for every endpoint.
        // This seems wasteful and should be handled globally, especially since it makes
        // refactoring error handling harder.
        // You may want to consider creating a parent class for all application
        // exceptions and encode the logic
        // to produce a HTTP output in that exception class.
        try {
            documentService.createDocument(createDocumentRequest);
            return GlobalExceptionHandler.buildSuccessResponse();
        } catch (JsonFileNotFoundException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("SCHEMA_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidJsonException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("INVALID_JSON", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidationException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("VALIDATION_ERROR", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /*
     * This method updates a document based on the provided ChangeDocumentRequest.
     *
     * @param changeDocumentRequest The request object containing the document data
     * to be updated.
     *
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PatchMapping("/update-document")
    public ResponseEntity<ApiResponse<Void>> updateDocument(@RequestBody ChangeDocumentRequest changeDocumentRequest) {
        try {
            documentService.updateDocument(changeDocumentRequest);
            return GlobalExceptionHandler.buildSuccessResponse();
        } catch (JsonFileNotFoundException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("SCHEMA_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidJsonException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("INVALID_JSON", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidationException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("VALIDATION_ERROR", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /*
     * This method retrieves a specific document by its ID.
     *
     * @param documentId The ID of the document to retrieve.
     *
     * @return ResponseEntity containing the Document object.
     */
    @GetMapping("/{documentId}")
    public ResponseEntity<ApiResponse<Document>> getDocumentById(@PathVariable("documentId") Integer documentId) {
        try {
            Document document = documentService.getDocumentById(documentId);
            return GlobalExceptionHandler.buildSuccessResponse(document);
        } catch (ResourceNotFoundException e) {
            return GlobalExceptionHandler.buildErrorResponse(
                    Map.of("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
