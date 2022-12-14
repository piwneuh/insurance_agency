import { City } from './city.model';

export class Address {
  street: string;
  streetNumber: string;
  city: City;

  constructor() {
    this.city = new City();
  }
}
