import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDocumentPageComponent } from './create-document-page.component';

describe('CreateDocumentPageComponent', () => {
  let component: CreateDocumentPageComponent;
  let fixture: ComponentFixture<CreateDocumentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateDocumentPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateDocumentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
