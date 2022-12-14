import { Address } from './address.model';
import { Contact } from './contact.model';

export class Person {
  firstName: string;
  lastName: string;
  maritalStatus: string;
  gender: string;
  jmbg: string;
  birth: string;
  contact: Contact;
  address: Address;

  constructor() {
    this.contact = new Contact();
    this.address = new Address();
  }
}
