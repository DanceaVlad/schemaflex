import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDocumentsPageComponent } from './view-documents-page.component';

describe('ViewDocumentsPageComponent', () => {
  let component: ViewDocumentsPageComponent;
  let fixture: ComponentFixture<ViewDocumentsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDocumentsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDocumentsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
