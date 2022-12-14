import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDriversToProposalComponent } from './add-drivers-to-proposal.component';

describe('AddDriversToProposalComponent', () => {
  let component: AddDriversToProposalComponent;
  let fixture: ComponentFixture<AddDriversToProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddDriversToProposalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDriversToProposalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
