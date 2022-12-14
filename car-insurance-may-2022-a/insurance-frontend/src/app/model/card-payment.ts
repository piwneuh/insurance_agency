import { CardType } from './card-type';

export interface CardPayment {
  cardType: CardType;
  cardHolder: string;
}
