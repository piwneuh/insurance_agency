import { BankPayment } from './bank-payment';
import { CardPayment } from './card-payment';
import { ChequePayment } from './cheque-payment';
import { PaymentMode } from './payment-mode';

export type AnyPayment = ChequePayment | CardPayment | BankPayment;

export interface Payment {
  paymentMode: PaymentMode;
  payment: AnyPayment;
}
