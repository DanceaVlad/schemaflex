import { Component, OnInit } from '@angular/core';

import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';

import { DocumentSchema } from '../../../data/DocumentSchema';
import { SchemaService } from '../../../services/schema.service';

@Component({
    selector: 'app-select-schema-page',
    imports: [MatListModule, MatButtonModule, RouterModule],
    templateUrl: './select-schema-page.component.html',
    styleUrl: './select-schema-page.component.scss',
    standalone: true,
})
export class SelectSchemaPageComponent implements OnInit {
    documentSchemas: DocumentSchema[] = [];

    constructor(private schemaService: SchemaService) {}

    ngOnInit(): void {
        this.schemaService.getAllDocumentSchemas().subscribe({
            next: (response) => {
                this.documentSchemas = response.sort((a, b) => a.id - b.id);
            },
        });
    }
}
