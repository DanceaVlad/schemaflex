import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { CreateDocumentRequest, Document, UpdateDocumentRequest } from '../data/Document';

@Injectable({
    providedIn: 'root',
})
export class DocumentService {
    private readonly apiUrl = `${environment.apiBase}/documents/`;

    constructor(private readonly http: HttpClient) { }

    createDocument(payload: CreateDocumentRequest): Observable<Document> {
        return this.http.post<Document>(this.apiUrl + 'create-document', payload);
    }

    updateDocument(payload: UpdateDocumentRequest): Observable<Document> {
        return this.http.put<Document>(this.apiUrl + 'update-document', payload);
    }

    getAllDocuments(): Observable<Document[]> {
        return this.http.get<Document[]>(this.apiUrl + 'all-documents');
    }

    getDocumentById(id: number): Observable<Document> {
        return this.http.get<Document>(this.apiUrl + id);
    }
}
