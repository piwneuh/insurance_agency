import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { City } from '../../model/city.model';
import { CountryService } from '../../services/country.service';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Country } from '../../model/country';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-manage-cities',
  templateUrl: './manage-cities.component.html',
  styleUrls: ['./manage-cities.component.css'],
})
export class ManageCitiesComponent implements OnInit {
  countries: Array<Country>;
  selectedCountry: Country;
  cities: Array<City>;
  cityToUpdate: City;
  cityBackup: City;
  cityToCreate: City = {} as City;
  cityToDelete: City;

  faEdit = faEdit;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(
    private countryService: CountryService,
    private modalService: NgbModal,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.countryService.getAllCountries().subscribe((data) => {
      this.countries = data;
    });
  }

  handleCountryChange() {
    this.countryService.getAllCities(this.selectedCountry.id).subscribe({
      next: (resp) => {
        this.cities = resp;
      },
    });
  }

  openCreate(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 'lg',
      })
      .result.then(
        () => {
          this.cityToCreate = {} as City;
        },
        () => {
          this.cityToCreate = {} as City;
        }
      );
  }

  openUpdate(content: any, city: City) {
    this.cityBackup = JSON.parse(JSON.stringify(city));
    this.cityToUpdate = city;
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 'lg',
      })
      .result.then(
        (reason) => {
          if (reason != 'update') {
            this.resetUpdate();
          }
        },
        () => {
          this.resetUpdate();
        }
      );
  }

  openDelete(content: any, city: City) {
    this.cityBackup = JSON.parse(JSON.stringify(city));
    this.cityToDelete = city;
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 's',
      })
      .result.then(
        () => {
          this.resetUpdate();
        },
        () => {
          this.resetUpdate();
        }
      );
  }

  update(modal: NgbModalRef) {
    this.countryService
      .updateCity(
        this.selectedCountry.id,
        this.cityToUpdate,
        this.cityToUpdate.id
      )
      .subscribe({
        next: () => {
          modal.close('update');
        },
        error: (err) => {
          this.snackBar.open(err.error.message, 'Ok', {
            panelClass: 'error-panel',
          });
          this.resetUpdate();
        },
        complete: () => {
          modal.close();
        },
      });
  }

  create(modal: NgbModalRef) {
    this.countryService
      .createCity(this.selectedCountry.id, this.cityToCreate)
      .subscribe({
        next: (resp) => {
          this.cities.push(resp);
        },
        error: (err) => {
          this.snackBar.open(err.error.message, 'Ok', {
            panelClass: 'error-panel',
          });
          this.cityToCreate = {} as City;
        },
        complete: () => {
          modal.close();
        },
      });
  }

  deleteCity(modal: NgbModalRef) {
    this.countryService
      .deleteCity(this.selectedCountry.id, this.cityToDelete.id)
      .subscribe({
        next: (resp) => {
          this.cities = this.cities.filter(
            (city) => city.id !== this.cityToDelete.id
          );
        },
        error: () => {
          this.cityToDelete = {} as City;
        },
        complete: () => {
          modal.close();
        },
      });
  }

  resetUpdate() {
    this.cityToUpdate = JSON.parse(JSON.stringify(this.cityBackup));
    this.cities = this.cities.map((city) =>
      city.id === this.cityToUpdate.id ? this.cityToUpdate : city
    );
  }
}
