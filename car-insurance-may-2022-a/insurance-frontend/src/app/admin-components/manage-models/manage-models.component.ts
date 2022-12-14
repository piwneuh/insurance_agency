import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Brand } from '../../model/brand';
import { Model } from '../../model/model';
import { BrandService } from '../../services/brand.service';

@Component({
  selector: 'app-manage-models',
  templateUrl: './manage-models.component.html',
  styleUrls: ['./manage-models.component.css'],
})
export class ManageModelsComponent implements OnInit {
  brands: Array<Brand>;
  selectedBrand: Brand;
  models: Array<Model>;
  modelToUpdate: Model;
  modelToDelete: Model;
  modelBackup: Model = {} as Model;
  modelToCreate: Model = {} as Model;

  faEdit = faEdit;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(
    private brandService: BrandService,
    private modalService: NgbModal,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.brandService.getAllBrands().subscribe({
      next: (resp) => {
        this.brands = resp;
      },
    });
  }

  handleBrandChange() {
    this.brandService.getAllModels(this.selectedBrand.id).subscribe({
      next: (resp) => {
        this.models = resp;
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
          this.modelToCreate = {} as Model;
        },
        () => {
          this.modelToCreate = {} as Model;
        }
      );
  }

  openUpdate(content: any, model: Model) {
    this.modelBackup = JSON.parse(JSON.stringify(model));
    this.modelToUpdate = model;
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

  openDelete(content: any, model: Model) {
    this.modelBackup = JSON.parse(JSON.stringify(model));
    this.modelToDelete = model;
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

  create(modal: NgbModalRef) {
    this.brandService
      .createModel(this.modelToCreate, this.selectedBrand.id)
      .subscribe({
        next: (resp) => {
          this.models.push(resp);
        },
        error: (err) => {
          this.snackBar.open(err.error.message, 'Ok', {
            panelClass: 'error-panel',
          });
          this.modelToCreate = {} as Model;
        },
        complete: () => {
          this.modelToCreate = {} as Model;
          modal.close();
        },
      });
  }

  update(modal: NgbModalRef) {
    this.brandService
      .updateModel(this.modelToUpdate, this.selectedBrand.id)
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

  deleteModel(modal: NgbModalRef) {
    this.brandService.deleteModel(this.selectedBrand.id, this.modelToDelete.id).subscribe({
      next: (resp) => {
        this.models = this.models.filter(
          (user) => user.id !== this.modelToDelete.id
        );
      },
      error: () => {
        this.modelToDelete = {} as Model;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  resetUpdate() {
    this.modelToUpdate = JSON.parse(JSON.stringify(this.modelBackup));
    this.models = this.models.map((model) =>
      model.id === this.modelToUpdate.id ? this.modelToUpdate : model
    );
  }

  addNewYear(model: Model) {
    if (!model.years) {
      model.years = [];
    }
    model.years.push({} as number);
  }

  removeYear(model: Model, index: number) {
    model.years.splice(index, 1);
  }

  trackYears(index: number, item: number) {
    return index;
  }
}
