import { InsuranceItem } from './insurance-item';

export interface InsurancePlan {
  id: number;
  name: string;
  isPremium: boolean;
  insuranceItems: Array<InsuranceItem>;
}
