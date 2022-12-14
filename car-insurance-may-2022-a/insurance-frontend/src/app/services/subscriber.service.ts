import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscriber } from '../model/subscriber.model';

@Injectable({
  providedIn: 'root',
})
export class SubscriberService {
  private _url = 'http://localhost:8080/api/subscribers';

  constructor(private http: HttpClient) {}

  public checkJmbg(jmbg: string): Observable<Subscriber> {
    return this.http.get<Subscriber>(`${this._url}/${jmbg}`);
  }
}
