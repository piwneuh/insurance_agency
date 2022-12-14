import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { faAngleLeft, faAngleRight } from '@fortawesome/free-solid-svg-icons';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Proposal } from '../model/proposal';
import { ProposalService } from '../services/proposal.service';

@Component({
  selector: 'app-proposal-page',
  templateUrl: './proposal-page.component.html',
  styleUrls: ['./proposal-page.component.css'],
})
export class ProposalPageComponent implements OnInit {
  checkedIfExists: boolean = false;

  addSubscriberCompleted: boolean = false;
  addCarCompleted: boolean = false;
  addDriversCompleted: boolean = false;
  addInsurancePlanCompleted: boolean = false;
  addPolicyCompleted: boolean = false;

  @ViewChild("refresh",{static:true}) content:ElementRef;

  faAngleLeft = faAngleLeft;
  faAngleRight = faAngleRight;

  proposal: Proposal;

  constructor(private proposalService: ProposalService,private modalService: NgbModal) {}

  ngOnInit(): void {
    this.checkedIfExists = true;
    this.openRefresh();
   
  }

  subscriberAdded(proposal: Proposal) {
    this.proposal = proposal;
    this.addSubscriberCompleted = true;
  }

  carAdded(proposal: Proposal) {
    this.proposal = proposal;
    this.addCarCompleted = true;
  }

  driversAdded(proposal: Proposal) {
    this.proposal = proposal;
    this.addDriversCompleted = true;
  }

  insurancePlanAdded(proposal: Proposal) {
    this.proposal = proposal;
    this.addInsurancePlanCompleted = true;
  }

  policyAdded(proposal: Proposal) {
    this.proposal = proposal;
    this.addPolicyCompleted = true;
  }

  validated(proposal: Proposal) { 
    this.proposal = proposal;
    localStorage.removeItem('proposalId');
  }

  loadProposal(modal: NgbModalRef){
    this.checkedIfExists = false;
    const proposalId = localStorage.getItem('proposalId');
    if (proposalId) {
      this.proposalService.getById(+proposalId).subscribe({
        next: (resp) => {
          this.proposal = resp;
          this.checkedIfExists = true;
        },
        error: () => {
          this.checkedIfExists = true;
        },
      });
    } else {
      this.checkedIfExists = true;
    }
    modal.close();
  }

  refreshProposal(modal: NgbModalRef){
    localStorage.removeItem("proposalId");
    modal.close();
  }

  openRefresh() {
    if(localStorage.getItem('proposalId')){
      this.modalService.open(this.content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 's',
      })
    }
  }

  
}
