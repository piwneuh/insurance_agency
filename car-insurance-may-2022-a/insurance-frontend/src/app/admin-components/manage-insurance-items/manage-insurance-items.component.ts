import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { InsuranceItem } from '../../model/insurance-item';
import { InsuranceItemService } from '../../services/insurance-item.service';

@Component({
  selector: 'app-manage-insurance-items',
  templateUrl: './manage-insurance-items.component.html',
  styleUrls: ['./manage-insurance-items.component.css'],
})
export class ManageInsuranceItemsComponent implements OnInit {
  insuranceItems: Array<InsuranceItem>;
  insuranceItemToUpdate: InsuranceItem;
  insuranceItemBackup: InsuranceItem = {} as InsuranceItem;
  insuranceItemToCreate: InsuranceItem = { isOptional: false } as InsuranceItem;
  insuranceItemToDelete: InsuranceItem;

  faEdit = faEdit;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(
    private insuranceItemService: InsuranceItemService,
    private modalService: NgbModal,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.insuranceItemService.getAll().subscribe({
      next: (resp) => {
        this.insuranceItems = resp;
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
          this.insuranceItemToCreate = { isOptional: false } as InsuranceItem;
        },
        () => {
          this.insuranceItemToCreate = { isOptional: false } as InsuranceItem;
        }
      );
  }

  openUpdate(content: any, insuranceItem: InsuranceItem) {
    this.insuranceItemBackup = JSON.parse(JSON.stringify(insuranceItem));
    this.insuranceItemToUpdate = insuranceItem;
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

   openDelete(content: any, insuranceItem: InsuranceItem) {
    this.insuranceItemBackup = JSON.parse(JSON.stringify(insuranceItem));
    this.insuranceItemToDelete = insuranceItem;
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
    this.insuranceItemService.update(this.insuranceItemToUpdate).subscribe({
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
    this.insuranceItemService.create(this.insuranceItemToCreate).subscribe({
      next: (resp) => {
        this.insuranceItems.push(resp);
      },
      error: (err) => {
        this.snackBar.open(err.error.message, 'Ok', {
          panelClass: 'error-panel',
        });
        this.insuranceItemToCreate = {} as InsuranceItem;
      },
      complete: () => {
        this.insuranceItemToCreate = { isOptional: false } as InsuranceItem;
        modal.close();
      },
    });
  }

  deleteInsuranceItem(modal: NgbModalRef) {
    this.insuranceItemService.delete(this.insuranceItemToDelete.id).subscribe({
      next: (resp) => {
        this.insuranceItems = this.insuranceItems.filter(
          (item) => item.id !== this.insuranceItemToDelete.id
        );
      },
      error: () => {
        this.insuranceItemToDelete = {} as InsuranceItem;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  resetUpdate() {
    this.insuranceItemToUpdate = JSON.parse(
      JSON.stringify(this.insuranceItemBackup)
    );
    this.insuranceItems = this.insuranceItems.map((insuranceItem) =>
      insuranceItem.id === this.insuranceItemToUpdate.id
        ? this.insuranceItemToUpdate
        : insuranceItem
    );
  }
}
