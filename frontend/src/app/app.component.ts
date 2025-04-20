import { Component, NO_ERRORS_SCHEMA, OnInit } from '@angular/core';

import { DateAdapter, MatNativeDateModule } from '@angular/material/core';
import { MatTabsModule } from '@angular/material/tabs';

import { JsonFormsModule } from '@jsonforms/angular';
import { JsonFormsAngularMaterialModule, angularMaterialRenderers } from '@jsonforms/angular-material';
import { Tester, and, createAjv, isControl, optionIs, rankWith, schemaTypeIs, scopeEndsWith } from '@jsonforms/core';
import { parsePhoneNumber } from 'libphonenumber-js';

import { CustomAutocompleteControlRenderer } from './custom.autocomplete';
import { DataDisplayComponent } from './data.control';
import { DocumentSchema } from './data/DocumentSchema';
import { LangComponent } from './lang.control';
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

    departmentTester: Tester = and(schemaTypeIs('string'), scopeEndsWith('department'));

    renderers = [
        ...angularMaterialRenderers,
        { tester: rankWith(5, this.departmentTester), renderer: CustomAutocompleteControlRenderer },
        {
            renderer: DataDisplayComponent,
            tester: rankWith(6, and(isControl, scopeEndsWith('___data'))),
        },
        {
            renderer: LangComponent,
            tester: rankWith(6, and(isControl, optionIs('lang', true))),
        },
    ];
    i18n = { locale: 'de-DE' };
    dateAdapter: any;
    ajv = createAjv({
        schemaId: 'id',
        allErrors: true,
    });

    constructor(
        private schemaService: SchemaService,
        dateAdapter: DateAdapter<Date>
    ) {
        this.ajv.addFormat('time', '^([0-1][0-9]|2[0-3]):[0-5][0-9]$');
        this.dateAdapter = dateAdapter;
        dateAdapter.setLocale(this.i18n.locale);
        this.ajv.addFormat('tel', (maybePhoneNumber) => {
            try {
                parsePhoneNumber(maybePhoneNumber, 'DE');
                return true;
            } catch (_) {
                return false;
            }
        });
    }

    ngOnInit(): void {
        this.schemaService.getAllDocumentSchemas().subscribe({
            next: (response) => {
                this.documentSchemas = response.sort((a, b) => a.id - b.id);
            },
        });
    }
}
