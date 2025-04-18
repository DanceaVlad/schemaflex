import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideToastr } from 'ngx-toastr';
import { AppComponent } from './app/app.component';
import { appConfig } from './app/app.config';
import { Interceptor } from './app/interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    ...appConfig.providers,
    provideAnimationsAsync(),
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true },
    provideToastr(),
  ],
}).catch((err) => console.error(err));
