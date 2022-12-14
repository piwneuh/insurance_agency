import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InsurancePlan } from '../model/insurance-plan';

@Injectable({
  providedIn: 'root',
})
export class InsurancePlanService {
  private _url = 'http://localhost:8080/api/insurance-plans';

  constructor(private http: HttpClient) {}

  public getAll(): Observable<Array<InsurancePlan>> {
    return this.http.get<Array<InsurancePlan>>(`${this._url}`);
  }

  public create(insurancePlan: InsurancePlan): Observable<InsurancePlan> {
    return this.http.post<InsurancePlan>(`${this._url}`, insurancePlan);
  }

  public update(insurancePlan: InsurancePlan): Observable<InsurancePlan> {
    return this.http.put<InsurancePlan>(
      `${this._url}/${insurancePlan.id}`,
      insurancePlan
    );
  }

  public delete(InsurancePlanId: number){
    return this.http.delete(`${this._url}/${InsurancePlanId}`)
  }
}
