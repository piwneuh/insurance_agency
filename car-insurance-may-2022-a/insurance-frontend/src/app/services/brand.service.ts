import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Brand } from '../model/brand';
import { Model } from '../model/model';

@Injectable({
  providedIn: 'root',
})
export class BrandService {
  private _url = 'http://localhost:8080/api/brands';

  constructor(private http: HttpClient) {}

  public getAllBrands() {
    return this.http.get<any>(`${this._url}`);
  }

  public getAllModels(brandId: number) {
    return this.http.get<any>(`${this._url}/${brandId}/models`);
  }

  public createBrand(brand: Brand): Observable<Brand> {
    return this.http.post<Brand>(`${this._url}`, brand);
  }

  public updateBrand(brand: Brand): Observable<Brand> {
    return this.http.put<Brand>(`${this._url}/${brand.id}`, brand);
  }

  public deleteBrand(brandId: number){
    return this.http.delete(`${this._url}/${brandId}`);
  }

  public createModel(model: Model, brandId: number): Observable<Model> {
    return this.http.post<Model>(`${this._url}/${brandId}/models`, model);
  }

  public updateModel(model: Model, brandId: number): Observable<Model> {
    return this.http.put<Model>(
      `${this._url}/${brandId}/models/${model.id}`,
      model
    );
  }

  public deleteModel(brandId: number, modelId: number){
    return this.http.delete(`${this._url}/${brandId}/models/${modelId}`);
  }
}
