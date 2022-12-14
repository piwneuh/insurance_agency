import { Accident } from './accident.model';
import { Person } from './person.model';
import { Risk } from './risk.model';

export class Driver extends Person {
  licenceNum: string;
  licenceObtained: string;
  yearsInsured: number;
  risks: Risk[];
  accidentHistories: Accident[];

  constructor() {
    super();
    this.risks = new Array<Risk>();
    this.accidentHistories = new Array<Accident>();
  }
}
