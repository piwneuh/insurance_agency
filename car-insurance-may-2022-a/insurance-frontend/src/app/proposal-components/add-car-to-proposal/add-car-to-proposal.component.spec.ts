import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCarToProposalComponent } from './add-car-to-proposal.component';

describe('AddCarToProposalComponent', () => {
  let component: AddCarToProposalComponent;
  let fixture: ComponentFixture<AddCarToProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCarToProposalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCarToProposalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
