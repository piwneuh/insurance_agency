import { Component, Input, OnInit } from '@angular/core';
import { Franchise } from '../../model/franchise';
import { InsuranceItem } from '../../model/insurance-item';
import { InsurancePlan } from '../../model/insurance-plan';

@Component({
  selector: 'app-insurance-plan-view',
  templateUrl: './insurance-plan-view.component.html',
  styleUrls: ['./insurance-plan-view.component.css'],
})
export class InsurancePlanViewComponent implements OnInit {
  @Input() insurancePlan: InsurancePlan;
  @Input() insuranceItems: Array<InsuranceItem>;
  @Input() franchises: Array<Franchise>;

  constructor() {}

  ngOnInit(): void {}
}
