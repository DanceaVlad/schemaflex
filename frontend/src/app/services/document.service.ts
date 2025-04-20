import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiResponse } from '../data/ApiResponse';
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
}
