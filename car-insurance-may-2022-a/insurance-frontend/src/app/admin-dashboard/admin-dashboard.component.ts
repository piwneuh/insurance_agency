import { Component, OnInit } from '@angular/core';
import {
  faCar,
  faCarOn,
  faCity,
  faEarthEurope,
  faShieldHalved,
  faFileShield,
  faTriangleExclamation,
  faUserGroup,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
})
export class AdminDashboardComponent implements OnInit {
  faEarthEurope = faEarthEurope;
  faCity = faCity;
  faCar = faCar;
  faCarOn = faCarOn;
  faShieldHalved = faShieldHalved;
  faFileShield = faFileShield;
  faTriangleExclamation = faTriangleExclamation;
  faUserGroup = faUserGroup;

  constructor() {}

  ngOnInit(): void {}
}
