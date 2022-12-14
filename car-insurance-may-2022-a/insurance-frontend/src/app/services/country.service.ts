import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { City } from '../model/city.model';
import { Country } from '../model/country';

@Injectable({
  providedIn: 'root',
})
export class CountryService {
  private _url = 'http://localhost:8080/api/countries';

  constructor(private http: HttpClient) {}

  public getAllCountries(): Observable<any> {
    return this.http.get<any>(this._url);
  }

  public getAllCities(countryId: number): Observable<any> {
    return this.http.get<any>(`${this._url}/${countryId}/cities`);
  }

  public createCountry(country: Country): Observable<Country> {
    return this.http.post<Country>(`${this._url}`, country);
  }

  public updateCountry(country: Country): Observable<Country> {
    return this.http.put<Country>(`${this._url}/${country.id}`, country);
  }

  public deleteCountry(countryId: number){
    return this.http.delete(`${this._url}/${countryId}`)
  }

  public createCity(countryId: number, city: City): Observable<City>{
    return this.http.post<City>(`${this._url}/${countryId}/cities`, city)
  }

  public updateCity(countryId: number, city: City, cityId: number): Observable<City>{
    return this.http.put<City>(`${this._url}/${countryId}/cities/${cityId}`, city)
  }

  public deleteCity(countryId:number, cityId: number){
    return this.http.delete(`${this._url}/${countryId}/cities/${cityId}`)
  }
}
