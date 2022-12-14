import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageInsuranceItemsComponent } from './manage-insurance-items.component';

describe('ManageInsuranceItemsComponent', () => {
  let component: ManageInsuranceItemsComponent;
  let fixture: ComponentFixture<ManageInsuranceItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageInsuranceItemsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageInsuranceItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
