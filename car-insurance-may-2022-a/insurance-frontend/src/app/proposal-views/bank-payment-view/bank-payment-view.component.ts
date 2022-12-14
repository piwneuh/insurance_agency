import { Component, Input, OnInit } from '@angular/core';
import { BankPayment } from '../../model/bank-payment';
import { AnyPayment } from '../../model/payment';

@Component({
  selector: 'app-bank-payment-view',
  templateUrl: './bank-payment-view.component.html',
  styleUrls: ['./bank-payment-view.component.css'],
})
export class BankPaymentViewComponent implements OnInit {
  @Input() payment: AnyPayment;

  bankPayment: BankPayment;

  constructor() {}

  ngOnInit(): void {
    this.bankPayment = this.payment as BankPayment;
  }
}
