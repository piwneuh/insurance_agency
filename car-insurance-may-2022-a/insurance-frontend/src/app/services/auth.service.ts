import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { LoginResponse } from '../model/login-response';
import { RefreshTokenResponse } from '../model/refreshTokenResponse';
import { User } from '../model/user';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _url = 'http://localhost:8080/api/auth';
  public currentUser = new BehaviorSubject<User>({} as User);

  constructor(
    private http: HttpClient,
    private jwtService: JwtService,
    private router: Router
  ) {
    this.populate();
  }

  login(credentials: any): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this._url}/login`, credentials, {
        withCredentials: true,
      })
      .pipe(
        map((resp) => {
          this.setAuth(resp);
          resp.user.role.name === 'ROLE_ADMIN'
            ? this.router.navigateByUrl('/admin')
            : this.router.navigateByUrl('/agent');
          return resp;
        })
      );
  }

  public changePassword(token: string, password: string) {
    return this.http.patch(`${this._url}/password`, {
      token: token,
      password: password,
    });
  }

  public requestPasswordChange(username: string) {
    return this.http.post(`${this._url}/password/${username}`, null);
  }

  populate() {
    if (this.jwtService.getToken()) {
      this.http.get<User>(`${this._url}/me`).subscribe({
        next: (resp) => {
          this.setCurrentUser(resp);
        },
        error: () => {
          this.logout();
        },
      });
    }
  }

  setAuth(loginResponse: LoginResponse) {
    this.jwtService.saveToken(loginResponse.accessToken);
    this.setCurrentUser(loginResponse.user);
  }

  setCurrentUser(user: User) {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  logout() {
    this.jwtService.destroyToken();
    localStorage.removeItem('currentUser');
    this.router.navigateByUrl('/login');
    return this.http.delete(`${this._url}/log-out`, { withCredentials: true });
  }

  autoLogin() {
    const currentUser = JSON.parse(
      localStorage.getItem('currentUser') || '{}'
    ) as User;
    if (!currentUser) {
      this.currentUser.next({} as User);
      return;
    }
    this.currentUser.next(currentUser);
  }

  refreshToken(): Observable<RefreshTokenResponse> {
    return this.http.get<RefreshTokenResponse>(`${this._url}/token/refresh`, {
      withCredentials: true,
    });
  }
}
