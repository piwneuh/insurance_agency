import { Component, Input, OnInit } from '@angular/core';
import { ChequePayment } from '../../model/cheque-payment';
import { AnyPayment } from '../../model/payment';

@Component({
  selector: 'app-cheque-payment-view',
  templateUrl: './cheque-payment-view.component.html',
  styleUrls: ['./cheque-payment-view.component.css'],
})
export class ChequePaymentViewComponent implements OnInit {
  @Input() payment: AnyPayment;

  chequePayment: ChequePayment;

  constructor() {}

  ngOnInit(): void {
    this.chequePayment = this.payment as ChequePayment;
  }
}
