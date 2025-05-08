import { HttpClient } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { Observable, map } from 'rxjs';

import { environment } from '../../environments/environment';
import { ApiResponse } from '../data/ApiResponse';
import { Schema } from '../data/Schema';

@Injectable({
    providedIn: 'root',
})
export class SchemaService {
    private readonly apiUrl = `${environment.apiBase}/schemas/`;

    constructor(private readonly http: HttpClient) {}

    getAllSchemas(): Observable<Schema[]> {
        return this.http.get<ApiResponse<Schema[]>>(this.apiUrl + 'all-schemas').pipe(
            map((response) => {
                return response.data;
            })
        );
    }

    getSchemaById(id: number): Observable<Schema> {
        return this.http.get<ApiResponse<Schema>>(this.apiUrl + 'schema/' + id).pipe(
            map((response) => {
                return response.data;
            })
        );
    }
}
