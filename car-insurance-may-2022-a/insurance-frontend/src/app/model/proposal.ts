import { Car } from './car';
import { Driver } from './driver.model';
import { Franchise } from './franchise';
import { InsuranceItem } from './insurance-item';
import { InsurancePlan } from './insurance-plan';
import { Policy } from './policy';
import { Subscriber } from './subscriber.model';

export interface Proposal {
  id: number;
  isValid: boolean;
  creationDate: string;
  subscriber: Subscriber;
  car: Car;
  drivers: Array<Driver>;
  insurancePlan: InsurancePlan;
  insuranceItems: Array<InsuranceItem>;
  franchises: Array<Franchise>;
  policy: Policy;
  amount: number;
}
