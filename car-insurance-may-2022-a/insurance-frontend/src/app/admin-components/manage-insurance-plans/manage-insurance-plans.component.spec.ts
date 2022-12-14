import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageInsurancePlansComponent } from './manage-insurance-plans.component';

describe('ManageInsurancePlansComponent', () => {
  let component: ManageInsurancePlansComponent;
  let fixture: ComponentFixture<ManageInsurancePlansComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageInsurancePlansComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageInsurancePlansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
