import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPolicyToProposalComponent } from './add-policy-to-proposal.component';

describe('AddPolicyToProposalComponent', () => {
  let component: AddPolicyToProposalComponent;
  let fixture: ComponentFixture<AddPolicyToProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPolicyToProposalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPolicyToProposalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
