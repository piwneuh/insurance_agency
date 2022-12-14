import { Model } from './model';

export interface Car {
  id: number;
  model: Model;
  licenceNum: string;
  year: number;
}
