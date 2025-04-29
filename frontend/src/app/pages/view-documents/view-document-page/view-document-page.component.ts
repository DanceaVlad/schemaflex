import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { JsonFormComponent } from '../../../component/json-form/json-form.component';

import { ToastrService } from 'ngx-toastr';

import { Document } from '../../../data/Document';
import { DocumentSchema } from '../../../data/DocumentSchema';
import { DocumentService } from '../../../services/document.service';
import { SchemaService } from '../../../services/schema.service';

@Component({
    selector: 'app-view-document-page',
    imports: [JsonFormComponent, MatIconModule, MatButtonModule],
    templateUrl: './view-document-page.component.html',
    styleUrl: './view-document-page.component.scss',
})
export class ViewDocumentPageComponent implements OnInit {
    document: Document | undefined;
    documentSchema: DocumentSchema | undefined;
    data: any = {};

    constructor(
        private toastr: ToastrService,
        private documentService: DocumentService,
        private schemaService: SchemaService,
        private router: Router
    ) {}

    ngOnInit(): void {
        const url = window.location.href;
        const documentId = url.split('/').pop();
        const documentIdNumber = Number(documentId);
        if (isNaN(documentIdNumber) || !documentIdNumber) {
            this.toastr.error('Invalid document ID in the URL', 'Error');
            return;
        }

        this.documentService.getDocumentById(documentIdNumber).subscribe({
            next: (document: Document) => {
                this.document = document;
                this.data = typeof document.data === 'string' ? JSON.parse(document.data) : document.data;
                this.schemaService.getDocumentSchemaById(document.schemaId).subscribe({
                    next: (schema: DocumentSchema) => {
                        this.documentSchema = schema;
                    },
                });
            },
        });
    }

    onSave() {
        if (!this.document) {
            this.toastr.error('Document not found', 'Error');
            return;
        }

        if (!this.documentSchema) {
            this.toastr.error('Schema not loaded', 'Error');
            return;
        }

        const payload = {
            id: this.document.id,
            data: JSON.stringify(this.data),
        };

        this.documentService.updateDocument(payload).subscribe({
            next: () => {
                this.toastr.success('Document updated successfully', 'Success');
                this.router.navigate(['/home']);
            },
            error: (error) => {
                this.toastr.error('Failed to update document', 'Error');
                this.router.navigate(['/home']);
            },
        });
    }
}
