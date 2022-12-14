import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InsuranceItem } from '../model/insurance-item';

@Injectable({
  providedIn: 'root',
})
export class InsuranceItemService {
  private _url = 'http://localhost:8080/api/insurance-items';

  constructor(private http: HttpClient) {}

  public getAll(): Observable<Array<InsuranceItem>> {
    return this.http.get<Array<InsuranceItem>>(`${this._url}`);
  }

  public create(insuranceItem: InsuranceItem): Observable<InsuranceItem> {
    return this.http.post<InsuranceItem>(`${this._url}`, insuranceItem);
  }

  public update(insurnaceItem: InsuranceItem): Observable<InsuranceItem> {
    return this.http.put<InsuranceItem>(
      `${this._url}/${insurnaceItem.id}`,
      insurnaceItem
    );
  }

  public delete(insuranceItemId: number){
    return this.http.delete(`${this._url}/${insuranceItemId}`)
  }
}
