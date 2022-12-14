import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { faFileCircleCheck, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { Proposal } from '../model/proposal';
import { ProposalService } from '../services/proposal.service';

@Component({
  selector: 'app-search-proposal',
  templateUrl: './search-proposal.component.html',
  styleUrls: ['./search-proposal.component.css'],
})
export class SearchProposalComponent implements OnInit {
  constructor(
    private proposalService: ProposalService,
    private snackBar: MatSnackBar
  ) {}

  proposalIdInput: number;
  proposal: Proposal;

  faMagnifyingGlass = faMagnifyingGlass;
  faFileCheck = faFileCircleCheck;

  ngOnInit(): void {
  }

  searchEnter(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.search();
    }
  }

  search() {
    if (!this.proposalIdInput) {
      return;
    }
    this.proposalService.getById(this.proposalIdInput).subscribe({
      next: (resp) => (this.proposal = resp),
      error: (err) => {
        if (err.error.status) {
          this.snackBar.open(`Proposal not found`, 'Ok', {panelClass:['blue-snackbar']});
          
        }
      },
    });
  }
}
