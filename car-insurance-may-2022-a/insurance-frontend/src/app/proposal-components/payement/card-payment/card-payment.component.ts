import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CardPayment } from '../../../model/card-payment';
import { CardType } from '../../../model/card-type';

@Component({
  selector: 'app-card-payment',
  templateUrl: './card-payment.component.html',
  styleUrls: ['./card-payment.component.css'],
})
export class CardPaymentComponent implements OnInit {
  @Input() cardTypes: Array<CardType>;
  @Input() completed: boolean;
  @Output() paymentEvent = new EventEmitter<CardPayment>();

  cardPayment: CardPayment = {} as CardPayment;

  constructor() {}

  ngOnInit(): void {}

  emitPaymentEvent() {
    this.paymentEvent.emit(this.cardPayment);
  }
}
