import { Component } from '@angular/core';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';

import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
    selector: 'app-save-dialog',
    imports: [ReactiveFormsModule, MatInputModule, MatButtonModule, MatCardModule],
    templateUrl: './save-dialog.component.html',
    styleUrl: './save-dialog.component.scss',
})
export class SaveDialogComponent {
    nameForm: FormGroup;

    constructor(
        private fb: FormBuilder,
        private dialogRef: MatDialogRef<SaveDialogComponent>
    ) {
        this.nameForm = fb.group({
            name: [''],
        });
    }

    onCancel(): void {
        this.dialogRef.close();
    }

    onSubmit() {
        if (this.nameForm.valid) {
            const name = this.nameForm.value.name.trim() ?? '';
            this.dialogRef.close(name);
        } else {
            console.error('Form is invalid');
        }
    }
}
