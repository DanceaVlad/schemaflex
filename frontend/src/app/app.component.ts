import { Component, OnInit } from '@angular/core';

import { RouterOutlet } from '@angular/router';

import { MatTabsModule } from '@angular/material/tabs';

import { DocumentSchema } from './data/DocumentSchema';
import { SchemaService } from './services/schema.service';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, MatTabsModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
    documentSchemas: DocumentSchema[] = [];

    constructor(private schemaService: SchemaService) {}

    ngOnInit(): void {
        this.schemaService.getAllDocumentSchemas().subscribe({
            next: (response) => {
                this.documentSchemas = response;
            },
        });
    }
}
