import { Component, OnInit, inject } from '@angular/core';

import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

import { DocumentDto, DocumentsService } from '@api/index';
import { DownloadDialogComponent } from '../download-dialog/download-dialog.component';

export interface DialogData {
    animal: string;
    name: string;
}

@Component({
    selector: 'app-view-documents-page',
    imports: [MatCardModule, MatIconModule, MatButtonModule, RouterModule],
    templateUrl: './view-documents-page.component.html',
    styleUrl: './view-documents-page.component.scss',
    standalone: true,
})
export class ViewDocumentsPageComponent implements OnInit {
    documents: DocumentDto[] = [];
    readonly dialog = inject(MatDialog);

    constructor(private documentService: DocumentsService) { }

    ngOnInit(): void {
        this.documentService.getAllDocuments().subscribe({
            next: (response) => {
                this.documents = response;
            },
        });
    }

    openDialog(): void {
        this.dialog.open(DownloadDialogComponent);
    }
}
