import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  password: string;
  confirmedPassword: string;
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  changePassword() {
    alert('test');
    if (this.password === this.confirmedPassword) {
      this.authService
        .changePassword(
          this.router.url.split('?')[1].split('=')[1],
          this.password
        )
        .subscribe(() => {
          this.router.navigateByUrl('/login');
        });
    } else {
      alert('Wrong password confirmation');
    }
  }

  passwordsMatch(): boolean {
    return this.password === this.confirmedPassword;
  }
}
