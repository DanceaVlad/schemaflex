import { Component, OnInit } from '@angular/core';

import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';

import { Schema } from '../../../data/Schema';
import { SchemaService } from '../../../services/schema.service';

@Component({
    selector: 'app-select-schema-page',
    imports: [MatListModule, MatButtonModule, RouterModule],
    templateUrl: './select-schema-page.component.html',
    styleUrl: './select-schema-page.component.scss',
    standalone: true,
})
export class SelectSchemaPageComponent implements OnInit {
    schemas: Schema[] = [];

    constructor(private schemaService: SchemaService) {}

    ngOnInit(): void {
        this.schemaService.getAllSchemas().subscribe({
            next: (response) => {
                this.schemas = response.sort((a, b) => a.id - b.id);
            },
        });
    }
}
