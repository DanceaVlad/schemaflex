import { Component, NO_ERRORS_SCHEMA, OnInit } from '@angular/core';

import { MatNativeDateModule } from '@angular/material/core';
import { MatTabsModule } from '@angular/material/tabs';

import { JsonFormsModule } from '@jsonforms/angular';
import { JsonFormsAngularMaterialModule, angularMaterialRenderers } from '@jsonforms/angular-material';

import { DocumentSchema } from './data/DocumentSchema';
import { SchemaService } from './services/schema.service';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [MatTabsModule, MatNativeDateModule, JsonFormsModule, JsonFormsAngularMaterialModule],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    schemas: [NO_ERRORS_SCHEMA],
})
export class AppComponent implements OnInit {
    documentSchemas: DocumentSchema[] = [];

    renderers = angularMaterialRenderers;

    data = {
        orders: [
            {
                customer: {
                    id: '471201',
                    name: 'Sirius Cybernetics Corporation',
                    department: 'Complaints Division',
                    phone: '(12) 34 56 78 90',
                },
                title: '42 killer robots',
                ordered: true,
                processId: 1890004498,
                assignee: 'Philip J. Fry',
                status: 'ordered',
                startDate: '2018-06-01',
                endDate: '2018-08-01',
            },
            {
                customer: {
                    id: '471202',
                    name: 'Very Big Corporation of America',
                    phone: '+49 123 456 789',
                },
                title: '1000 gallons of MomCorp Oil',
                processId: 1890004499,
                assignee: 'Jen Barber',
                startDate: '2018-07-01',
                status: 'planned',
            },
        ],
    };

    constructor(private schemaService: SchemaService) {}

    ngOnInit(): void {
        this.schemaService.getAllDocumentSchemas().subscribe({
            next: (response) => {
                this.documentSchemas = response.sort((a, b) => a.id - b.id);
            },
        });
    }
}
