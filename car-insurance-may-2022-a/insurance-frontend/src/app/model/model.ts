import { Brand } from './brand';

export interface Model {
  id: number;
  name: string;
  years: Array<number>;
  brand: Brand;
}
