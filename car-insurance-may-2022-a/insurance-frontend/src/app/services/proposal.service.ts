import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../model/car';
import { Driver } from '../model/driver.model';
import { Policy } from '../model/policy';
import { Proposal } from '../model/proposal';

@Injectable({
  providedIn: 'root',
})
export class ProposalService {
  private _url = 'http://localhost:8080/api/proposals';

  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get<Proposal[]>(`${this._url}`);
  }

  public getById(id: number): Observable<Proposal> {
    return this.http.get<Proposal>(`${this._url}/${id}`);
  }

  public addSubscriber(subscriber: any): Observable<Proposal> {
    return this.http.post<Proposal>(`${this._url}`, subscriber);
  }

  public addCarToProposal(proposalId: number, car: Car): Observable<Proposal> {
    return this.http.patch<Proposal>(`${this._url}/${proposalId}/car`, car);
  }

  public addDriversToProposal(
    proposalId: number,
    drivers: Array<Driver>
  ): Observable<Proposal> {
    return this.http.patch<Proposal>(
      `${this._url}/${proposalId}/drivers`,
      drivers
    );
  }

  public addInsuranceToProposal(
    proposalId: number,
    insurance: any
  ): Observable<Proposal> {
    return this.http.patch<Proposal>(
      `${this._url}/${proposalId}/insurance`,
      insurance
    );
  }

  public addPolicyToProposal(
    proposalId: number,
    policy: Policy
  ): Observable<Proposal> {
    return this.http.patch<Proposal>(
      `${this._url}/${proposalId}/policy`,
      policy
    );
  }

  public validate(proposalId: number): Observable<Proposal> {
    return this.http.patch<Proposal>(
      `${this._url}/${proposalId}/validate`,
      null
    );
  }

  public generateReport(proposalId: number): Observable<Blob> {
    return this.http.get(`${this._url}/${proposalId}/report`, {
      responseType: 'blob',
      headers: new HttpHeaders({ accept: 'application/pdf' }),
    });
  }
}
