import { Component, NO_ERRORS_SCHEMA, OnInit } from '@angular/core';

import { MatNativeDateModule } from '@angular/material/core';
import { MatTabsModule } from '@angular/material/tabs';

import { JsonFormsModule } from '@jsonforms/angular';
import { JsonFormsAngularMaterialModule, angularMaterialRenderers } from '@jsonforms/angular-material';
import { ToastrService } from 'ngx-toastr';

import { DocumentSchema } from './data/DocumentSchema';
import { SaveDocumentRequest } from './data/SaveDocumentRequest';
import { DocumentService } from './services/document.service';
import { SchemaService } from './services/schema.service';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [MatTabsModule, MatNativeDateModule, JsonFormsModule, JsonFormsAngularMaterialModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    schemas: [NO_ERRORS_SCHEMA],
})
export class AppComponent implements OnInit {
    documentSchemas: DocumentSchema[] = [];

    renderers = angularMaterialRenderers;

    data = {};

    constructor(
        private schemaService: SchemaService,
        private documentService: DocumentService,
        private toastr: ToastrService
    ) {}

    ngOnInit(): void {
        this.schemaService.getAllDocumentSchemas().subscribe({
            next: (response) => {
                this.documentSchemas = response.sort((a, b) => a.id - b.id);
            },
        });
    }

    onSave(schemaId: number) {
        const payload: SaveDocumentRequest = {
            schemaId: schemaId,
            data: this.data,
        };
        this.documentService.saveDocument(payload).subscribe({
            next: (response) => {
                this.toastr.success('Document saved successfully', 'Success');
            },
        });
    }
}
