import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

import { ApplicationConfig } from '@angular/core';

import { Routes, provideRouter, withComponentInputBinding } from '@angular/router';

import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

import { CreateDocumentPageComponent } from './pages/create-document/create-document-page/create-document-page.component';
import { SelectSchemaPageComponent } from './pages/create-document/select-schema-page/select-schema-page.component';
import { HomeComponent } from './pages/home/home.component';
import { ViewDocumentsPageComponent } from './pages/view-documents-page/view-documents-page.component';

const routes: Routes = [
    { path: 'create-document/:id', component: CreateDocumentPageComponent },
    { path: 'select-schema', component: SelectSchemaPageComponent },
    { path: 'view-documents', component: ViewDocumentsPageComponent },
    { path: '', component: HomeComponent },
    { path: '**', redirectTo: '' },
];

export const appConfig: ApplicationConfig = {
    providers: [provideAnimationsAsync(), provideHttpClient(withInterceptorsFromDi()), provideRouter(routes, withComponentInputBinding())],
};
