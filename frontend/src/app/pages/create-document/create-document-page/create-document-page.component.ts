import { Component, NO_ERRORS_SCHEMA } from '@angular/core';

import { Router } from '@angular/router';

import { MatNativeDateModule } from '@angular/material/core';
import { MatListModule } from '@angular/material/list';

import { JsonFormComponent } from '../../../component/json-form/json-form.component';

import { JsonFormsModule } from '@jsonforms/angular';
import { JsonFormsAngularMaterialModule, angularMaterialRenderers } from '@jsonforms/angular-material';
import { ToastrService } from 'ngx-toastr';

import { CreateDocumentRequest } from '../../../data/Document';
import { DocumentSchema } from '../../../data/DocumentSchema';
import { DocumentService } from '../../../services/document.service';
import { SchemaService } from '../../../services/schema.service';

@Component({
    selector: 'app-create-document-page',
    imports: [MatListModule, MatNativeDateModule, JsonFormsModule, JsonFormsAngularMaterialModule, JsonFormComponent, JsonFormComponent],
    templateUrl: './create-document-page.component.html',
    styleUrl: './create-document-page.component.scss',
    standalone: true,
    schemas: [NO_ERRORS_SCHEMA],
})
export class CreateDocumentPageComponent {
    documentSchema: DocumentSchema | undefined;
    renderers = angularMaterialRenderers;

    data: any = {};

    constructor(
        private schemaService: SchemaService,
        private documentService: DocumentService,
        private toastr: ToastrService,
        private router: Router
    ) {}

    ngOnInit(): void {
        const url = window.location.href;
        const schemaId = url.split('/').pop();
        const schemaIdNumber = Number(schemaId);
        if (isNaN(schemaIdNumber) || !schemaIdNumber) {
            this.toastr.error('Invalid schema ID in the URL', 'Error');
            return;
        }

        this.schemaService.getDocumentSchemaById(schemaIdNumber).subscribe({
            next: (schema: DocumentSchema) => {
                this.documentSchema = schema;
            },
        });
    }

    onSave() {
        if (!this.documentSchema) {
            this.toastr.error('Schema not loaded', 'Error');
            return;
        }
        const payload: CreateDocumentRequest = {
            schemaId: this.documentSchema.id,
            data: this.data,
        };
        this.documentService.createDocument(payload).subscribe({
            next: (response) => {
                this.toastr.success('Document created successfully', 'Success');
                this.router.navigate(['/home']);
            },
        });
    }
}
