import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { JsonFormComponent } from '../../../component/json-form.component';

import { CustomSchemaDto, DocumentDto, DocumentsService, SchemasService, UpdateDocumentRequestDto } from '@api/index';
import { ToastrService } from 'ngx-toastr';


@Component({
    selector: 'app-view-document-page',
    imports: [JsonFormComponent, MatIconModule, MatButtonModule],
    templateUrl: './view-document-page.component.html',
    styleUrl: './view-document-page.component.scss',
})
export class ViewDocumentPageComponent implements OnInit {
    document: DocumentDto | undefined;
    schema: CustomSchemaDto | undefined;
    data: any = {};

    constructor(
        private toastr: ToastrService,
        private documentService: DocumentsService,
        private schemaService: SchemasService,
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
            next: (document: DocumentDto) => {
                this.document = document;
                this.data = typeof document.data === 'string' ? JSON.parse(document.data) : document.data;
                this.schemaService.getSchemaById(document.schemaId).subscribe({
                    next: (schema: CustomSchemaDto) => {
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

        const payload: UpdateDocumentRequestDto = {
            id: this.document.id!,
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
