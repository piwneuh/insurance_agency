import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentModesResponse } from '../model/payment-modes-response';

@Injectable({
  providedIn: 'root',
})
export class PaymentModeService {
  private _url = 'http://localhost:8080/api/payment-modes';

  constructor(private http: HttpClient) {}

  public getAll(): Observable<PaymentModesResponse> {
    return this.http.get<PaymentModesResponse>(this._url);
  }
}
