import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddInsurancePlanToProposalComponent } from './add-insurance-plan-to-proposal.component';

describe('AddInsurancePlanToProposalComponent', () => {
  let component: AddInsurancePlanToProposalComponent;
  let fixture: ComponentFixture<AddInsurancePlanToProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddInsurancePlanToProposalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddInsurancePlanToProposalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
