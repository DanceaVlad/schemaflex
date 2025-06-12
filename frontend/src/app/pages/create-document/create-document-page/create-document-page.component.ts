import { Component, NO_ERRORS_SCHEMA, inject } from '@angular/core';

import { Router } from '@angular/router';

import { MatNativeDateModule } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';

import { JsonFormComponent } from '../../../component/json-form/json-form.component';

import { JsonFormsModule } from '@jsonforms/angular';
import { JsonFormsAngularMaterialModule, angularMaterialRenderers } from '@jsonforms/angular-material';
import { ToastrService } from 'ngx-toastr';

import { CreateDocumentRequestDto, CustomSchemaDto, DocumentsService, SchemasService } from '@api/index';
import { SaveDialogComponent } from '../save-dialog/save-dialog.component';

@Component({
    selector: 'app-create-document-page',
    imports: [MatListModule, MatNativeDateModule, JsonFormsModule, JsonFormsAngularMaterialModule, JsonFormComponent, JsonFormComponent],
    templateUrl: './create-document-page.component.html',
    styleUrl: './create-document-page.component.scss',
    standalone: true,
    schemas: [NO_ERRORS_SCHEMA],
})
export class CreateDocumentPageComponent {
    schema: CustomSchemaDto | undefined;
    renderers = angularMaterialRenderers;
    readonly dialog = inject(MatDialog);

    data: any = {};

    constructor(
        private schemaService: SchemasService,
        private documentService: DocumentsService,
        private toastr: ToastrService,
        private router: Router
    ) { }

    ngOnInit(): void {
        const url = window.location.href;
        const schemaId = url.split('/').pop();
        const schemaIdNumber = Number(schemaId);
        if (isNaN(schemaIdNumber) || !schemaIdNumber) {
            this.toastr.error('Invalid schema ID in the URL', 'Error');
            return;
        }

        this.schemaService.getSchemaById(schemaIdNumber).subscribe({
            next: (schema: CustomSchemaDto) => {
                this.schema = schema;
            },
        });
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(SaveDialogComponent);

        dialogRef.afterClosed().subscribe((name: string) => {
            if (name) {
                const payload: CreateDocumentRequestDto = {
                    name: name,
                    schemaId: this.schema!.id!,
                    data: this.data,
                };

                this.documentService.createDocument(payload).subscribe({
                    next: () => {
                        this.toastr.success('Document created successfully', 'Success');
                        this.router.navigate(['/view-documents']);
                    },
                });
            }
        });
    }
}
