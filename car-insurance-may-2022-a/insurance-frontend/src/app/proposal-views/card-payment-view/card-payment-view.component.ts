import { Component, Input, OnInit } from '@angular/core';
import { CardPayment } from '../../model/card-payment';
import { AnyPayment } from '../../model/payment';

@Component({
  selector: 'app-card-payment-view',
  templateUrl: './card-payment-view.component.html',
  styleUrls: ['./card-payment-view.component.css'],
})
export class CardPaymentViewComponent implements OnInit {
  @Input() payment: AnyPayment;

  cardPayment: CardPayment;

  constructor() {}

  ngOnInit(): void {
    this.cardPayment = this.payment as CardPayment;
  }
}
