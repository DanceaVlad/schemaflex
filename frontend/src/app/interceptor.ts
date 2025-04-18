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
    ) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                switch (error.error.errors[0].code) {
                    case 'SCHEMA_NOT_FOUND':
                        this.toastr.error(error.error.errors[0].message, 'Schema not found', {
                            timeOut: 3000,
                            positionClass: 'toast-top-right',
                            progressBar: true,
                            progressAnimation: 'increasing',
                            closeButton: true,
                            tapToDismiss: true,
                        });
                        break;
                    case 'INVALID_JSON':
                        this.toastr.error(error.error.errors[0].message, 'Invalid Json', {
                            timeOut: 3000,
                            positionClass: 'toast-top-right',
                            progressBar: true,
                            progressAnimation: 'increasing',
                            closeButton: true,
                            tapToDismiss: true,
                        });
                        break;

                    default:
                        break;
                }

                return throwError(() => error);
            })
        );
    }
}
