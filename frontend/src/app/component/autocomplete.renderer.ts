import {debounceTime, startWith, map} from 'rxjs/operators';
import {AutocompleteControlRenderer} from '@jsonforms/angular-material';
import {Component} from '@angular/core';
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from "@angular/material/autocomplete";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {ReactiveFormsModule} from "@angular/forms";
import {AsyncPipe} from "@angular/common";
import {MatInput} from "@angular/material/input";

@Component({
    selector: 'autocomplete-renderer',
    standalone: true,
    styles: `
        mat-form-field {
            flex: 1 1 auto;
            width: 100%;
        }
    `,
    imports: [MatFormField, MatLabel, MatAutocomplete, MatOption, MatProgressSpinner, ReactiveFormsModule, MatAutocompleteTrigger, MatError, AsyncPipe, MatInput],
    template: `
        <mat-form-field>
            <mat-label>{{ label }}</mat-label>
            <input
                matInput
                type="text"
                (input)="onChange($event)"
                placeholder="{{ description }}"
                [id]="id"
                [formControl]="form"
                [matAutocomplete]="auto"
            >
            <mat-autocomplete
                autoActiveFirstOption #auto="matAutocomplete" (optionSelected)="onSelect($event)">
                @if (isLoading) {
                    <mat-option class="is-loading"><mat-spinner diameter="30"></mat-spinner></mat-option>
                } @else {
                    @for (option of filteredOptions | async; track option) {
                        <mat-option [value]="option">
                            {{ option }}
                        </mat-option>
                    }
                }
            </mat-autocomplete>
            <mat-error>{{ error }}</mat-error>
        </mat-form-field>
    `,
})
export class AutocompleteRenderer extends AutocompleteControlRenderer {

    isLoading: boolean = false;
    private staticOptions: string[] = [];

    override ngOnInit() {
        super.ngOnInit();

        this.staticOptions = this.getStaticOptions();

        this.filteredOptions = this.form.valueChanges.pipe(
            startWith(''),
            debounceTime(150),
            map(value => this.filterOptions(value || ''))
        );
    }

    private getStaticOptions(): string[] {
        // Static options defined in the schema
        const uiSchemaOptions = this.uischema?.options;
        if (uiSchemaOptions && uiSchemaOptions['options']) {
            return uiSchemaOptions['options'] as string[];
        }

        // Dynamic options can be fetched from a service.
        // For example, in the UI schema you might have an endpoint path.
        // You would then call that endpoint here to fetch options.

        // For now, we will return a static list of options.
        // These will not be shown because the UI schema does already define the options.
        return [
            'Option 1',
            'Option 2',
            'Option 3',
            'Option 4',
        ];
    }

    private filterOptions(input: string): string[] {
        if (!input || input.trim() === '') {
            return this.staticOptions;
        }

        return this.staticOptions.filter(option =>
            option.toLowerCase().includes(input.toLowerCase())
        );
    }
}
