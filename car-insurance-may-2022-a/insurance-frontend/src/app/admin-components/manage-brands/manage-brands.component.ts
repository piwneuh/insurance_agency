import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Brand } from '../../model/brand';
import { BrandService } from '../../services/brand.service';

@Component({
  selector: 'app-manage-brands',
  templateUrl: './manage-brands.component.html',
  styleUrls: ['./manage-brands.component.css'],
})
export class ManageBrandsComponent implements OnInit {
  brands: Array<Brand>;
  brandToUpdate: Brand;
  brandBackup: Brand = {} as Brand;
  brandToCreate: Brand = {} as Brand;
  brandToDelete: Brand;

  faEdit = faEdit;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(
    private brandService: BrandService,
    private modalService: NgbModal,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.brandService.getAllBrands().subscribe((resp) => {
      this.brands = resp;
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
          this.brandToCreate = {} as Brand;
        },
        () => {
          this.brandToCreate = {} as Brand;
        }
      );
  }

  openUpdate(content: any, brand: Brand) {
    this.brandBackup = JSON.parse(JSON.stringify(brand));
    this.brandToUpdate = brand;
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

  openDelete(content: any, brand: Brand) {
    this.brandBackup = JSON.parse(JSON.stringify(brand));
    this.brandToDelete = brand;
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
    this.brandService.updateBrand(this.brandToUpdate).subscribe({
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
    this.brandService.createBrand(this.brandToCreate).subscribe({
      next: (resp) => {
        this.brands.push(resp);
      },
      error: (err) => {
        this.snackBar.open(err.error.message, 'Ok', {
          panelClass: 'error-panel',
        });
        this.brandToCreate = {} as Brand;
      },
      complete: () => {
        this.brandToCreate = {} as Brand;
        modal.close();
      },
    });
  }

  deleteBrand(modal: NgbModalRef) {
    this.brandService.deleteBrand(this.brandToDelete.id).subscribe({
      next: (resp) => {
        this.brands = this.brands.filter(
          (risk) => risk.id !== this.brandToDelete.id
        );
      },
      error: () => {
        this.brandToDelete = {} as Brand;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  resetUpdate() {
    this.brandToUpdate = JSON.parse(JSON.stringify(this.brandBackup));
    this.brands = this.brands.map((brand) =>
      brand.id === this.brandToUpdate.id ? this.brandToUpdate : brand
    );
  }
}
