import { Franchise } from './franchise';
import { InsuranceItem } from './insurance-item';
import { InsurancePlan } from './insurance-plan';

export interface Insurance {
  insurancePlan: InsurancePlan;
  insuranceItems: Array<InsuranceItem>;
  franchises: Array<Franchise>;
}
