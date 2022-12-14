import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { InsuranceItem } from '../../model/insurance-item';
import { InsurancePlan } from '../../model/insurance-plan';
import { InsuranceItemService } from '../../services/insurance-item.service';
import { InsurancePlanService } from '../../services/insurance-plan.service';

@Component({
  selector: 'app-manage-insurance-plans',
  templateUrl: './manage-insurance-plans.component.html',
  styleUrls: ['./manage-insurance-plans.component.css'],
})
export class ManageInsurancePlansComponent implements OnInit {
  insurancePlans: Array<InsurancePlan>;
  insurancePlanToUpdate: InsurancePlan;
  insurancePlanBackup: InsurancePlan = {} as InsurancePlan;
  insurancePlanToCreate: InsurancePlan = { isPremium: false } as InsurancePlan;
  insurancePlanToDelete: InsurancePlan;

  insuranceItems: Array<InsuranceItem>;

  faEdit = faEdit;
  faPlus = faPlus;
  faTrash = faTrash;

  dropdownSettings: IDropdownSettings = {
    singleSelection: false,
    idField: 'id',
    textField: 'name',
    unSelectAllText: 'Unselect all',
    itemsShowLimit: 3,
    allowSearchFilter: true,
    enableCheckAll: false,
  };

  constructor(
    private insurancePlanService: InsurancePlanService,
    private insuranceItemService: InsuranceItemService,
    private modalService: NgbModal,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.insurancePlanService.getAll().subscribe((resp) => {
      this.insurancePlans = resp;
    });
    this.insuranceItemService.getAll().subscribe((resp) => {
      this.insuranceItems = resp;
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
          this.insurancePlanToCreate = { isPremium: false } as InsurancePlan;
        },
        () => {
          this.insurancePlanToCreate = { isPremium: false } as InsurancePlan;
        }
      );
  }

  openUpdate(content: any, brand: InsurancePlan) {
    this.insurancePlanBackup = JSON.parse(JSON.stringify(brand));
    this.insurancePlanToUpdate = brand;
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 'lg',
      })
      .result.then(
        (reason) => {
          if (reason !== 'update') {
            this.resetUpdate();
          }
        },
        () => {
          this.resetUpdate();
        }
      );
  }

  openDelete(content: any, insurancePlan: InsurancePlan) {
    this.insurancePlanBackup = JSON.parse(JSON.stringify(insurancePlan));
    this.insurancePlanToDelete = insurancePlan;
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
    this.insurancePlanService.update(this.insurancePlanToUpdate).subscribe({
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
    this.insurancePlanService.create(this.insurancePlanToCreate).subscribe({
      next: (resp) => {
        this.insurancePlans.push(resp);
      },
      error: (err) => {
        this.snackBar.open(err.error.message, 'Ok', {
          panelClass: 'error-panel',
        });
        this.insurancePlanToCreate = {} as InsurancePlan;
      },
      complete: () => {
        this.insurancePlanToCreate = { isPremium: false } as InsurancePlan;
        modal.close();
      },
    });
  }

  deleteInsurancePlan(modal: NgbModalRef) {
    this.insurancePlanService.delete(this.insurancePlanToDelete.id).subscribe({
      next: (resp) => {
        this.insurancePlans = this.insurancePlans.filter(
          (plan) => plan.id !== this.insurancePlanToDelete.id
        );
      },
      error: () => {
        this.insurancePlanToDelete = {} as InsurancePlan;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  resetUpdate() {
    this.insurancePlanToUpdate = JSON.parse(
      JSON.stringify(this.insurancePlanBackup)
    );
    this.insurancePlans = this.insurancePlans.map((insurancePlan) =>
      insurancePlan.id === this.insurancePlanToUpdate.id
        ? this.insurancePlanToUpdate
        : insurancePlan
    );
  }

  addNewInsuranceItem(insurancePlan: InsurancePlan) {
    if (!insurancePlan.insuranceItems) {
      insurancePlan.insuranceItems = [];
    }
    insurancePlan.insuranceItems.push({} as InsuranceItem);
  }

  removeInsuranceItem(insurancePlan: InsurancePlan, index: number) {
    insurancePlan.insuranceItems.splice(index, 1);
  }

  trackItems(index: number, item: InsuranceItem) {
    return index;
  }
}
