import { Component, Input, OnInit } from '@angular/core';
import { Car } from '../../model/car';

@Component({
  selector: 'app-car-view',
  templateUrl: './car-view.component.html',
  styleUrls: ['./car-view.component.css'],
})
export class CarViewComponent implements OnInit {
  @Input() car: Car;

  constructor() {}

  ngOnInit(): void {}
}
