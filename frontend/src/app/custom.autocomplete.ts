import { Component } from '@angular/core';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { Observable, of } from 'rxjs';
import { debounceTime, delay, finalize, switchMap, tap } from 'rxjs/operators';

import { ReactiveFormsModule } from '@angular/forms';
import { AutocompleteControlRenderer } from '@jsonforms/angular-material';
import { generate } from 'random-words';

const words: string[] = generate(1000) as string[];

const fetchSuggestions = (input: string): Observable<string[]> => {
    const filtered: string[] = words.filter((word) => word.startsWith(input));
    return of(filtered).pipe(delay(1000));
};

@Component({
    selector: 'jsonforms-custom-autocomplete',
    template: `
        <mat-form-field fxFlex>
            <mat-label>{{ label }}</mat-label>
            <input matInput type="text" (input)="onChange($event)" placeholder="{{ description }}" [id]="id" [formControl]="form" [matAutocomplete]="auto" />
            <mat-autocomplete autoActiveFirstOption #auto="matAutocomplete" (optionSelected)="onSelect($event)">
                <mat-option *ngIf="isLoading" class="is-loading"><mat-spinner diameter="30"></mat-spinner></mat-option>
                <ng-container *ngIf="!isLoading">
                    <mat-option *ngFor="let option of filteredOptions | async" [value]="option">
                        {{ option }}
                    </mat-option>
                </ng-container>
            </mat-autocomplete>
            <mat-error>{{ error }}</mat-error>
        </mat-form-field>
    `,
    standalone: true,
    imports: [MatFormFieldModule, MatInputModule, MatAutocompleteModule, MatOptionModule, MatProgressSpinnerModule, ReactiveFormsModule],
})
export class CustomAutocompleteControlRenderer extends AutocompleteControlRenderer {
    isLoading: boolean = false;

    override ngOnInit() {
        super.ngOnInit();
        this.form.valueChanges
            .pipe(
                debounceTime(300),
                tap(() => (this.isLoading = true)),
                switchMap((value) => fetchSuggestions(value).pipe(finalize(() => (this.isLoading = false))))
            )
            .subscribe((options: string[]) => (this.options = options));
    }
}
