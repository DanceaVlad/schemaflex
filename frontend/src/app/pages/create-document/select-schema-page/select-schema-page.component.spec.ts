import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectSchemaPageComponent } from './select-schema-page.component';

describe('SelectSchemaPageComponent', () => {
  let component: SelectSchemaPageComponent;
  let fixture: ComponentFixture<SelectSchemaPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelectSchemaPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelectSchemaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
