import {
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { ChequePayment } from '../../../model/cheque-payment';
import { faCalendar } from '@fortawesome/free-solid-svg-icons';
import { NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { Payment } from '../../../model/payment';

@Component({
  selector: 'app-cheque-payment',
  templateUrl: './cheque-payment.component.html',
  styleUrls: ['./cheque-payment.component.css'],
})
export class ChequePaymentComponent implements OnInit {
  @Input() completed: boolean;
  @Output() paymentEvent = new EventEmitter<ChequePayment>();

  chequePayment: ChequePayment = {} as ChequePayment;
  faCalendar = faCalendar;

  constructor() {}

  ngOnInit(): void {}

  chequeDateSelected(date: NgbDate) {
    this.chequePayment.chequeDate = this.formatDate(date);
    this.emitPaymentEvent();
  }

  formatDate(date: NgbDate): string {
    let year = date.year.toString();
    let month =
      date.month.toString().length === 1
        ? `0${date.month.toString()}`
        : date.month.toString();
    let day =
      date.day.toString().length === 1
        ? `0${date.day.toString()}`
        : date.day.toString();

    return `${year}-${month}-${day} 00:00`;
  }

  emitPaymentEvent() {
    this.paymentEvent.emit(this.chequePayment);
  }
}
