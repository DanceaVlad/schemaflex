import { Component, OnInit, inject, model, signal } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

import { Document } from '../../../data/Document';
import { DocumentService } from '../../../services/document.service';
import { DownloadDialogComponent } from '../download-dialog/download-dialog.component';

export interface DialogData {
    animal: string;
    name: string;
}

@Component({
    selector: 'app-view-documents-page',
    imports: [MatCardModule, MatIconModule, MatButtonModule],
    templateUrl: './view-documents-page.component.html',
    styleUrl: './view-documents-page.component.scss',
    standalone: true,
})
export class ViewDocumentsPageComponent implements OnInit {
    documents: Document[] = [];
    readonly dialog = inject(MatDialog);
    readonly animal = signal('');
    readonly name = model('');

    constructor(private documentService: DocumentService) {}

    ngOnInit(): void {
        this.documentService.getAllDocuments().subscribe({
            next: (response) => {
                this.documents = response;
            },
        });
    }

    openDialog(): void {
        const dialogRef = this.dialog.open(DownloadDialogComponent, {
            data: { name: this.name(), animal: this.animal() },
        });

        dialogRef.afterClosed().subscribe((result) => {
            console.log('The dialog was closed');
            if (result !== undefined) {
                this.animal.set(result);
            }
        });
    }
}
