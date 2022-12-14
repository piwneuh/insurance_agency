import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from '../model/role';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private _url = 'http://localhost:8080/api/auth/users';

  constructor(private http: HttpClient) {}

  public getAllUsers() {
    return this.http.get<Array<User>>(`${this._url}`);
  }

  public getAllRoles() {
    return this.http.get<Array<Role>>(`${this._url}/roles`);
  }

  public createUser(user: User) {
    return this.http.post<User>(`${this._url}`, user);
  }

  public updateUser(user: User) {
    return this.http.put<User>(`${this._url}/${user.id}`, user);
  }

  public deleteUser(userId: number) {
    return this.http.delete<Number>(`${this._url}/${userId}`);
  }
}
