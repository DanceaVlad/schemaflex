import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDocumentPageComponent } from './view-document-page.component';

describe('ViewDocumentPageComponent', () => {
  let component: ViewDocumentPageComponent;
  let fixture: ComponentFixture<ViewDocumentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDocumentPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDocumentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
