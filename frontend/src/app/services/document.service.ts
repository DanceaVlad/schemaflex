import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { Observable, map } from 'rxjs';

import { environment } from '../../environments/environment';
import { ApiResponse } from '../data/ApiResponse';
import { CreateDocumentRequest, Document, UpdateDocumentRequest } from '../data/Document';

@Injectable({
    providedIn: 'root',
})
export class DocumentService {
    private readonly apiUrl = `${environment.apiBase}/documents/`;

    constructor(private readonly http: HttpClient) { }

    createDocument(payload: CreateDocumentRequest): Observable<ApiResponse> {
        return this.http.post<ApiResponse>(this.apiUrl + 'create-document', payload);
    }

    updateDocument(payload: UpdateDocumentRequest): Observable<ApiResponse> {
        return this.http.patch<ApiResponse>(this.apiUrl + 'update-document', payload);
    }

    getAllDocuments(): Observable<Document[]> {
        return this.http.get<ApiResponse<Document[]>>(this.apiUrl + 'all-documents').pipe(map((response) => response.data));
    }

    getDocumentById(id: number): Observable<Document> {
        return this.http.get<ApiResponse<Document>>(this.apiUrl + id).pipe(map((response) => response.data));
    }
}
