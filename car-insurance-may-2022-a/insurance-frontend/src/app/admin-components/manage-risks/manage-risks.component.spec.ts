import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageRisksComponent } from './manage-risks.component';

describe('ManageRisksComponent', () => {
  let component: ManageRisksComponent;
  let fixture: ComponentFixture<ManageRisksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageRisksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageRisksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
