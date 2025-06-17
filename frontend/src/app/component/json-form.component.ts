import { Component, EventEmitter, Input, NO_ERRORS_SCHEMA, Output } from '@angular/core';
import { CustomSchemaDto } from '@api/index';

import { JsonFormsModule } from '@jsonforms/angular';
import { JsonFormsAngularMaterialModule, angularMaterialRenderers } from '@jsonforms/angular-material';
import {and, optionIs, rankWith, schemaTypeIs, Tester, UISchemaElement} from "@jsonforms/core";
import {AutocompleteRenderer} from "./autocomplete.renderer";

const autocompleteTester: Tester = and(
    schemaTypeIs('string'),
    optionIs('autocomplete', true),
    //scopeEndsWith('autocomplete'),
);

@Component({
    selector: 'app-json-form',
    imports: [JsonFormsModule, JsonFormsAngularMaterialModule],
    template: `
        <div class="d-flex h-100 justify-content-center align-items-center">
        @if (schema && schema.dataSchema) {
            <div class="jsonforms-wrapper">
                <jsonforms [schema]="schema.dataSchema" [uischema]="getUiSchema()" [(data)]="data" [renderers]="renderers"
                    (dataChange)="onDataChange($event)"> </jsonforms>
            </div>
        }
        </div>
    `,
    styles: `
        .jsonforms-wrapper {
            width: 70vw;
            height: 100%;
            min-width: 70vw;
        }
    `,
    standalone: true,
    schemas: [NO_ERRORS_SCHEMA],
})
export class JsonFormComponent {
    @Input({ required: true })
    schema: CustomSchemaDto | undefined;
    @Input()
    data = {};
    @Output()
    dataChange = new EventEmitter<any>();

    renderers = [
        ...angularMaterialRenderers,
        {tester: rankWith(5, autocompleteTester), renderer: AutocompleteRenderer}
        ];

    getUiSchema(): UISchemaElement {
        return this.schema?.uiSchema as UISchemaElement|| {
            type: 'VerticalLayout',
            elements: []
        };
    }


    onDataChange(updatedData: any) {
        this.data = updatedData;
        this.dataChange.emit(updatedData);
    }
}
