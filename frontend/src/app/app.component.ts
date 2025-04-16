import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SchemaService } from './services/schema.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(private schemaService: SchemaService) {}

  ngOnInit(): void {
    this.schemaService.getAllDocumentSchemas().subscribe({
      next: (response) => {
        console.log('Schemas:', response);
      },
      error: (error) => {
        console.error('Error fetching schemas:', error);
      },
    });
  }
}
