import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProposalPageComponent } from './proposal-page.component';

describe('ProposalPageComponent', () => {
  let component: ProposalPageComponent;
  let fixture: ComponentFixture<ProposalPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProposalPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProposalPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
