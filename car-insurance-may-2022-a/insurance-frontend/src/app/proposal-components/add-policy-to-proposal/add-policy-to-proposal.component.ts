import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AnyPayment } from '../../model/payment';
import { PaymentModesResponse } from '../../model/payment-modes-response';
import { Policy } from '../../model/policy';
import { Proposal } from '../../model/proposal';
import { PaymentModeService } from '../../services/payment-mode.service';
import { ProposalService } from '../../services/proposal.service';

@Component({
  selector: 'app-add-policy-to-proposal',
  templateUrl: './add-policy-to-proposal.component.html',
  styleUrls: ['./add-policy-to-proposal.component.css'],
})
export class AddPolicyToProposalComponent implements OnInit {
  @Input() proposal: Proposal;
  @Output() completedEvent = new EventEmitter<Proposal>();

  completed: boolean = false;

  paymentModesResponse: PaymentModesResponse;
  policy: Policy = { payment: { payment: {} as AnyPayment } } as Policy;
  proposalId: string = localStorage.getItem('proposalId') || '';

  constructor(
    private paymentModeService: PaymentModeService,
    private proposalService: ProposalService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (this.proposal?.policy) {
      this.policy = this.proposal.policy;
      this.completed = true;
      return;
    }
    this.paymentModeService.getAll().subscribe({
      next: (resp) => {
        this.paymentModesResponse = resp;
      },
    });
  }

  resetPayment() {
    this.policy.payment.payment = {} as AnyPayment;
  }

  addPayment(payment: AnyPayment) {
    this.policy.payment.payment = payment;
  }

  addPolicy() {
    this.proposalService
      .addPolicyToProposal(this.proposal.id, this.policy)
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
