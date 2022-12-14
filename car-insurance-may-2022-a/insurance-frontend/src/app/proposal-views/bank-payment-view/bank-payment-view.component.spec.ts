import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankPaymentViewComponent } from './bank-payment-view.component';

describe('BankPaymentViewComponent', () => {
  let component: BankPaymentViewComponent;
  let fixture: ComponentFixture<BankPaymentViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BankPaymentViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BankPaymentViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
