import { Component, OnInit } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { User } from '../model/user';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css'],
})
export class NavBarComponent implements OnInit {
  currentUser: User;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.currentUser.subscribe((user) => (this.currentUser = user));
  }

  logout() {
    this.authService.logout().subscribe();
  }
}
