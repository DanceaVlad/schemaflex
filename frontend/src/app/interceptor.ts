import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';

import { Injectable } from '@angular/core';

import { Router } from '@angular/router';

import { Observable, catchError, throwError } from 'rxjs';

import { ToastrService } from 'ngx-toastr';

@Injectable()
export class Interceptor implements HttpInterceptor {
    constructor(
        private router: Router,
        private toastr: ToastrService
    ) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                // Handle backend error format: { "ERROR_TYPE": "message" }
                if (error.error && typeof error.error === 'object' && !Array.isArray(error.error)) {
                    const keys = Object.keys(error.error);
                    if (keys.length > 0) {
                        const errorType = keys[0];
                        const message = error.error[errorType];
                        let title = errorType.replace(/_/g, ' ').toLowerCase();
                        title = title.charAt(0).toUpperCase() + title.slice(1);
                        this.toastr.error(message, title, {
                            timeOut: 3000,
                            positionClass: 'toast-top-right',
                            progressBar: true,
                            progressAnimation: 'increasing',
                            closeButton: true,
                            tapToDismiss: true,
                        });
                    } else {
                        this.toastr.error('An unknown error occurred', 'Error');
                    }
                } else {
                    this.toastr.error('An unknown error occurred', 'Error');
                }
                return throwError(() => error);
            })
        );
    }
}
