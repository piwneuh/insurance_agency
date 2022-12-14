import { Component, Input, OnInit } from '@angular/core';
import { Driver } from '../../model/driver.model';

@Component({
  selector: 'app-driver-view',
  templateUrl: './driver-view.component.html',
  styleUrls: ['./driver-view.component.css'],
})
export class DriverViewComponent implements OnInit {
  @Input() driver: Driver;

  constructor() {}

  ngOnInit(): void {}
}
