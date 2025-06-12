import { Component, OnInit } from '@angular/core';

import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { CustomSchemaDto, SchemasService } from '@api/index';


@Component({
    selector: 'app-select-schema-page',
    imports: [MatListModule, MatButtonModule, RouterModule],
    templateUrl: './select-schema-page.component.html',
    styleUrl: './select-schema-page.component.scss',
    standalone: true,
})
export class SelectSchemaPageComponent implements OnInit {
    schemas: CustomSchemaDto[] = [];

    constructor(private schemaService: SchemasService) { }

    ngOnInit(): void {
        this.schemaService.getAllSchemas().subscribe({
            next: (response) => {
                this.schemas = response.sort((a, b) => a.id! - b.id!);
            },
        });
    }
}
