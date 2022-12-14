import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Insurance } from '../../model/insurance';
import { InsurancePlan } from '../../model/insurance-plan';
import { InsurancePlanService } from '../../services/insurance-plan.service';
import { faAward, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { InsuranceItem } from '../../model/insurance-item';
import { Franchise } from '../../model/franchise';
import { ProposalService } from '../../services/proposal.service';
import { Proposal } from '../../model/proposal';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-insurance-plan-to-proposal',
  templateUrl: './add-insurance-plan-to-proposal.component.html',
  styleUrls: ['./add-insurance-plan-to-proposal.component.css'],
})
export class AddInsurancePlanToProposalComponent implements OnInit {
  @Input() proposal: Proposal;
  @Output() completedEvent = new EventEmitter<Proposal>();

  completed: boolean = false;

  insurancePlans: Array<InsurancePlan>;
  selectedInsurancePlan: any;
  insurance: Insurance = {} as Insurance;
  franchises: Array<Franchise> = [];
  percentages: Array<number> = [];

  optionalItems: Array<InsuranceItem> = [];
  mandatoryItems: Array<InsuranceItem> = [];

  selected: boolean = false;
  faAward = faAward;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(
    private insurancePlanService: InsurancePlanService,
    private proposalService: ProposalService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (this.proposal?.insurancePlan) {
      this.completed = true;
      this.insurance = {
        insurancePlan: this.proposal.insurancePlan,
        insuranceItems: this.proposal.insuranceItems,
        franchises: this.proposal.franchises,
      };
      return;
    }
    this.getInsurancePlans();
  }

  getInsurancePlans() {
    this.insurancePlanService.getAll().subscribe((resp) => {
      this.insurancePlans = resp;
    });
  }

  addFranchise(insuranceItem: InsuranceItem, percentage: number) {
    this.franchises.push({
      insuranceItem: insuranceItem,
      percentage: percentage,
    });
    this.optionalItems = this.optionalItems.filter(
      (item) => item.id !== insuranceItem.id
    );
  }

  removeFranchise(franchise: Franchise) {
    this.franchises.splice(this.franchises.indexOf(franchise), 1);
    this.optionalItems.push(franchise.insuranceItem);
  }

  insurancePlanChanged() {
    this.selectedInsurancePlan = this.insurancePlans.find(
      (plan) => plan.id === this.insurance.insurancePlan.id
    );

    this.optionalItems = [];
    this.mandatoryItems = [];

    for (let item of this.selectedInsurancePlan.insuranceItems) {
      if (item.isOptional) {
        this.optionalItems.push(item);
      } else {
        this.mandatoryItems.push(item);
      }
    }
    this.selected = true;
  }

  addInsuranceToProposal() {
    this.insurance.franchises = this.franchises;
    this.insurance.insurancePlan = this.selectedInsurancePlan;
    this.insurance.insuranceItems = this.mandatoryItems;

    this.proposalService
      .addInsuranceToProposal(this.proposal.id, this.insurance)
      .subscribe({
        next: (resp) => {
          this.completed = true;
          this.completedEvent.emit(resp);
        },
        error: () => this.snackbar.open('Please, fill out all the fields.', 'Ok', {panelClass:['blue-snackbar']}),
        complete: () => this.snackbar.open('Successfully added!', 'Ok', {panelClass:['blue-snackbar']})
      });
  }
}
