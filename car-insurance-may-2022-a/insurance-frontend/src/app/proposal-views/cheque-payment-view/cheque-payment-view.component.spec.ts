import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChequePaymentViewComponent } from './cheque-payment-view.component';

describe('ChequePaymentViewComponent', () => {
  let component: ChequePaymentViewComponent;
  let fixture: ComponentFixture<ChequePaymentViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChequePaymentViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChequePaymentViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
