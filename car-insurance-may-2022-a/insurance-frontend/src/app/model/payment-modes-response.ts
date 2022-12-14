import { CardType } from './card-type';
import { PaymentMode } from './payment-mode';

export interface PaymentModesResponse {
  paymentModes: Array<PaymentMode>;
  cardTypes: Array<CardType>;
}
