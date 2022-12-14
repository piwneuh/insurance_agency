import { Component, Input, OnInit } from '@angular/core';
import { Policy } from '../../model/policy';

@Component({
  selector: 'app-policy-view',
  templateUrl: './policy-view.component.html',
  styleUrls: ['./policy-view.component.css'],
})
export class PolicyViewComponent implements OnInit {
  @Input() policy: Policy;

  constructor() {}

  ngOnInit(): void {}
}
