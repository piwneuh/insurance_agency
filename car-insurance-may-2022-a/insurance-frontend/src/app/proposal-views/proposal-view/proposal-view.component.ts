import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Proposal } from '../../model/proposal';
import { ProposalService } from '../../services/proposal.service';

@Component({
  selector: 'app-proposal-view',
  templateUrl: './proposal-view.component.html',
  styleUrls: ['./proposal-view.component.css'],
})
export class ProposalViewComponent implements OnInit {
  @Input() proposal: Proposal;
  @Output() completedEvent = new EventEmitter<Proposal>();

  constructor(private proposalService: ProposalService, private snackbar: MatSnackBar) {}

  ngOnInit(): void {}

  validate() {
    this.proposalService.validate(this.proposal.id).subscribe({
      next: (resp) => {
        this.completedEvent.emit(resp);
        this.proposal = resp;
      },
      complete: () => this.snackbar.open('Successfully validated!', '', {panelClass:['blue-snackbar']})
    });
  }

  generateReport() {
    this.proposalService.generateReport(this.proposal.id).subscribe({
      next: (resp) => {
        const file = new Blob([resp], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      },
    });
  }
}
