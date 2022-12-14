import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Risk } from '../../model/risk.model';
import { DriverService } from '../../services/driver.service';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-manage-risks',
  templateUrl: './manage-risks.component.html',
  styleUrls: ['./manage-risks.component.css']
})
export class ManageRisksComponent implements OnInit {

  risks: Array<Risk>
  riskToUpdate: Risk;
  riskBackup: Risk = {} as Risk;
  riskToCreate: Risk = {} as Risk;
  riskToDelete: Risk;


  faEdit = faEdit;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(private driverService: DriverService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.driverService.getAllRisks().subscribe((data) => {
      this.risks = data;
    });
  }

  close(modal: NgbModalRef) {
    modal.close();
  }

  openCreate(content: any) {
    this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
      size: 'lg',
    });
  }

  openUpdate(content: any, risk: Risk) {
    this.riskBackup = JSON.parse(JSON.stringify(risk));
    this.riskToUpdate = risk;
    this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
      size: 'lg',
    });
  }

  openDelete(content: any, risk: Risk) {
    this.riskBackup = JSON.parse(JSON.stringify(risk));
    this.riskToDelete = risk;
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
    this.driverService.createRisk(this.riskToUpdate).subscribe({
      next: () => {
        modal.close();
      },
      error: () => {
        this.resetUpdate();
      },
      complete: () => {
        modal.close();
      },
    });
  }

  create(modal: NgbModalRef) {
    this.driverService.createRisk(this.riskToCreate).subscribe({
      next: (resp) => {
        this.risks = resp;
      },
      error: () => {
        this.riskToCreate = {} as Risk;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  deleteRisk(modal: NgbModalRef) {
    this.driverService.deleteRisk(this.riskToDelete.id).subscribe({
      next: (resp) => {
        this.risks = this.risks.filter(
          (risk) => risk.id !== this.riskToDelete.id
        );
      },
      error: () => {
        this.riskToDelete = {} as Risk;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  resetUpdate() {
    this.riskToUpdate = JSON.parse(JSON.stringify(this.riskBackup));
    this.risks = this.risks.map((country) =>
      country.id === this.riskToUpdate.id ? this.riskToUpdate : country
    );
  }
}
