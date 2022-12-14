import { Component, OnInit } from '@angular/core';
import { Country } from '../../model/country';
import { CountryService } from '../../services/country.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-manage-countries',
  templateUrl: './manage-countries.component.html',
  styleUrls: ['./manage-countries.component.css'],
})
export class ManageCountriesComponent implements OnInit {
  countries: Array<Country>;
  countryToUpdate: Country;
  countryBackup: Country = {} as Country;
  countryToCreate: Country = {} as Country;
  countryToDelete: Country;

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

  openCreate(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 'lg',
      })
      .result.then(
        () => {
          this.countryToCreate = {} as Country;
        },
        () => {
          this.countryToCreate = {} as Country;
        }
      );
  }

  openUpdate(content: any, country: Country) {
    this.countryBackup = JSON.parse(JSON.stringify(country));
    this.countryToUpdate = country;
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

  openDelete(content: any, country: Country) {
    this.countryBackup = JSON.parse(JSON.stringify(country));
    this.countryToDelete = country;
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
    this.countryService.updateCountry(this.countryToUpdate).subscribe({
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
    this.countryService.createCountry(this.countryToCreate).subscribe({
      next: (resp) => {
        this.countries.push(resp);
      },
      error: (err) => {
        this.snackBar.open(err.error.message, 'Ok', {
          panelClass: 'error-panel',
        });
        this.countryToCreate = {} as Country;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  deleteCountry(modal: NgbModalRef) {
    this.countryService.deleteCountry(this.countryToDelete.id).subscribe({
      next: (resp) => {
        this.countries = this.countries.filter(
          (country) => country.id !== this.countryToDelete.id
        );
      },
      error: () => {
        this.countryToDelete = {} as Country;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  resetUpdate() {
    this.countryToUpdate = JSON.parse(JSON.stringify(this.countryBackup));
    this.countries = this.countries.map((country) =>
      country.id === this.countryToUpdate.id ? this.countryToUpdate : country
    );
  }
}
