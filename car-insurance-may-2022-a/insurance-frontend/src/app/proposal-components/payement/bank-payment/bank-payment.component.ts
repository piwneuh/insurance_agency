import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BankPayment } from '../../../model/bank-payment';

@Component({
  selector: 'app-bank-payment',
  templateUrl: './bank-payment.component.html',
  styleUrls: ['./bank-payment.component.css'],
})
export class BankPaymentComponent implements OnInit {
  @Input() completed: boolean;
  @Output() paymentEvent = new EventEmitter<BankPayment>();

  bankPayment: BankPayment = {} as BankPayment;

  constructor() {}

  ngOnInit(): void {}

  emitPaymentEvent() {
    this.paymentEvent.emit(this.bankPayment);
  }
  show() {
    console.log(this.completed);
  }
}
