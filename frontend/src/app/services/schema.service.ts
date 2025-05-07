import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { Observable, map } from 'rxjs';

import { environment } from '../../environments/environment';
import { ApiResponse } from '../data/ApiResponse';
import { DocumentSchema } from '../data/DocumentSchema';


@Injectable({
    providedIn: 'root',
})
export class SchemaService {
    private readonly apiUrl = `${environment.apiBase}/schemas/`;

    constructor(private readonly http: HttpClient) { }

    getAllDocumentSchemas(): Observable<DocumentSchema[]> {
        return this.http.get<ApiResponse<DocumentSchema[]>>(this.apiUrl + 'all-schemas').pipe(map((response) => response.data));
    }
    getDocumentSchemaById(id: number): Observable<DocumentSchema> {
        return this.http.get<ApiResponse<DocumentSchema>>(this.apiUrl + 'schema/' + id).pipe(map((response) => response.data));
    }
}
