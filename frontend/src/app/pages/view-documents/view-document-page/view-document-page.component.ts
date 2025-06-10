import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { JsonFormComponent } from '../../../component/json-form/json-form.component';

import { ToastrService } from 'ngx-toastr';

import { Document } from '../../../data/Document';
import { Schema } from '../../../data/Schema';
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
    schema: Schema | undefined;
    data: any = {};

    constructor(
        private toastr: ToastrService,
        private documentService: DocumentService,
        private schemaService: SchemaService,
        private router: Router
    ) { }

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
                this.schemaService.getSchemaById(document.schemaId).subscribe({
                    next: (schema: Schema) => {
                        this.schema = schema;
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

        if (!this.schema) {
            this.toastr.error('Schema not loaded', 'Error');
            return;
        }

        const payload = {
            id: this.document.id,
            data: this.data,
        };

        this.documentService.updateDocument(payload).subscribe({
            next: () => {
                this.toastr.success('Document updated successfully', 'Success');
                this.router.navigate(['/home']);
            },
        });
    }
}
