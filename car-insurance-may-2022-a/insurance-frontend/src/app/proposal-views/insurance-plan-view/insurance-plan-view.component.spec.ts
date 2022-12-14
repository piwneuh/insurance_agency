import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsurancePlanViewComponent } from './insurance-plan-view.component';

describe('InsurancePlanViewComponent', () => {
  let component: InsurancePlanViewComponent;
  let fixture: ComponentFixture<InsurancePlanViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InsurancePlanViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InsurancePlanViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
