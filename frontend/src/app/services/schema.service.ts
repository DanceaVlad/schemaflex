import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { ApiResponse } from '../data/ApiResponse';
import { DocumentSchema } from '../data/DocumentSchema';

@Injectable({
    providedIn: 'root',
})
export class SchemaService {
    private readonly apiUrl = 'http://localhost:8080/schemas/';

    constructor(private readonly http: HttpClient) {}

    getAllDocumentSchemas() {
        return this.http.get<ApiResponse<DocumentSchema[]>>(this.apiUrl + 'all-schemas');
    }
}
