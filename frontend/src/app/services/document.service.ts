import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { Observable, map } from 'rxjs';

import { ApiResponse } from '../data/ApiResponse';
import { Document } from '../data/Document';
import { SaveDocumentRequest } from '../data/SaveDocumentRequest';

@Injectable({
    providedIn: 'root',
})
export class DocumentService {
    private readonly apiUrl = 'http://localhost:8080/documents/';

    constructor(private readonly http: HttpClient) {}

    saveDocument(payload: SaveDocumentRequest): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiUrl + 'save-document', payload);
    }

    getAllDocuments(): Observable<Document[]> {
        return this.http.get<ApiResponse<Document[]>>(this.apiUrl + 'all-documents').pipe(map((response) => response.data));
    }
}
