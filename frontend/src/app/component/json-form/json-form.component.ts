import { Component, EventEmitter, Input, NO_ERRORS_SCHEMA, Output } from '@angular/core';

import { JsonFormsModule } from '@jsonforms/angular';
import { JsonFormsAngularMaterialModule, angularMaterialRenderers } from '@jsonforms/angular-material';

import { Schema } from '../../data/Schema';

@Component({
    selector: 'app-json-form',
    imports: [JsonFormsModule, JsonFormsAngularMaterialModule],
    templateUrl: './json-form.component.html',
    styleUrl: './json-form.component.scss',
    standalone: true,
    schemas: [NO_ERRORS_SCHEMA],
})
export class JsonFormComponent {
    @Input({ required: true })
    schema: Schema | undefined;
    @Input()
    data = {};
    @Output()
    dataChange = new EventEmitter<any>();

    renderers = angularMaterialRenderers;

    onDataChange(updatedData: any) {
        this.data = updatedData;
        this.dataChange.emit(updatedData);
    }
}
