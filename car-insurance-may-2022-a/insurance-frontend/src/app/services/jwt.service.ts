import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class JwtService {
  getToken(): string {
    return localStorage['access-token'];
  }

  saveToken(token: string) {
    localStorage['access-token'] = token;
  }

  destroyToken() {
    localStorage.removeItem('access-token');
  }
}
