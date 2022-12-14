import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  forgot: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  login() {
    let credentials = {
      username: this.username,
      password: this.password,
    };
    this.authService.login(credentials).subscribe({
      error: (err) =>
        this.snackbar.open(err.error.message, '', {
          panelClass: ['blue-snackbar'],
        }),
    });
  }

  requestPasswordChange() {
    if (!this.username) {
      this.snackbar.open('Please enter username', 'Ok', {
        panelClass: ['blue-snackbar'],
      });
      return;
    }
    this.authService.requestPasswordChange(this.username).subscribe();
    this.forgot = true;
  }
}
