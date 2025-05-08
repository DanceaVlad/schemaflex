import { Component } from '@angular/core';

import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-home',
    imports: [RouterModule, MatButtonModule],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss',
    standalone: true,
})
export class HomeComponent {
    constructor() {}
}
