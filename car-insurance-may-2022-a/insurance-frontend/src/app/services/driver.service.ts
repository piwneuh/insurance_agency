import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Driver } from '../model/driver.model';
import { Risk } from '../model/risk.model';

@Injectable({
  providedIn: 'root',
})
export class DriverService {
  private _url = 'http://localhost:8080/api/drivers';

  constructor(private http: HttpClient, private router: Router) {}

  public checkJmbg(jmbg: string): Observable<Driver> {
    return this.http.get<Driver>(`${this._url}/${jmbg}`);
  }

  public getAllRisks(): Observable<Array<Risk>> {
    return this.http.get<Array<Risk>>(`${this._url}/risks`);
  }

  public createRisk(risk: Risk): Observable<Array<Risk>>  {
    return this.http.post<Array<Risk>>(`${this._url}/risks`, risk);
  }

  public deleteRisk(riskId: number){
    return this.http.delete(`${this._url}/risks/${riskId}`);
  }
}
