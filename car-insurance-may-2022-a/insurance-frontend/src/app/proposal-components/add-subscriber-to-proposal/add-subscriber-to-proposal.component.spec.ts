import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSubscriberToProposalComponent } from './add-subscriber-to-proposal.component';

describe('AddSubscriberToProposalComponent', () => {
  let component: AddSubscriberToProposalComponent;
  let fixture: ComponentFixture<AddSubscriberToProposalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSubscriberToProposalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSubscriberToProposalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
